package simpleRPGgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RPGFrame extends JFrame implements ActionListener {
	int current_charID = 1;
	Character currentChar;
	String[][] charList; 
	private final int CHAR_ID = 0;
	private final int CHAR_NAME = 1;
	private final int ST_NAME = 1;
	private final int ST_ITEM = 2;
	private final int ST_HP = 4;
	private final int ST_MONEY = 8;
	private final int ST_EXP = 16;
	private final int ST_WEPATK = 32;
	private final int ST_ARMDEF = 64;
	private final int ST_SPD = 128;
	private final int ST_INT = 256;
	private final int ST_ALL = 511;
			
	Town curTown;
	Town nextTown;
	
	Enemy curEnemy;
	
	Events eventList;

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	private JPanel titlePanel = new JPanel();
	private JLabel lblTitle = new JLabel("Simple RPG Game");
	private JLabel lblSubtitle = new JLabel("<html>A game by Team 6 with Connor Jacobson, Nolan Miller, and Ronald Miller.</html>");
	private JTextField nameField = new JTextField();
	private JButton btnNewGame = new JButton("New Game");
	private JButton btnContinueGame = new JButton("Continue Game");
	private JComboBox cboLoad = new JComboBox();
	private JLabel lblStartName = new JLabel("Name:");
	private JLabel lblLoadCharacter = new JLabel("Load Character:");
	private JButton btnDeleteCharacter = new JButton("Delete Character");
	
	private JPanel townPanel = new JPanel();	
	private JLabel lblTownname = new JLabel("Welcome to Startstown!");
	private JComboBox cboItemBuy = new JComboBox();
	private JLabel lblReminder = new JLabel("<html>Remember to buy items and spend your EXP before moving on... <br>(if the + buttons are available, it means you have EXP to spend.)<br>\r\nYour progress is saved when entering and leaving a town.</html>");
	private JButton btnBuyItem = new JButton("Buy Item");
	private JLabel lblBuyItemDesc = new JLabel("<html><b>Potion</b> - Healing - 10 Coins<br>\r\nHeals 25 HP upon use.</html>");
	private JButton btnSaveNow = new JButton("Save now");
	private JButton btnNextTown = new JButton("Proceed to Woodstown");
	
	private JPanel travelPanel = new JPanel();
	private JProgressBar pbDistance = new JProgressBar();
	private JLabel lblDistance = new JLabel("15 kilometers left to Woodstown");
	private JButton btnMoveOn = new JButton("Move on");
	private JLabel lblTraveling = new JLabel("Traveling to Woodstown...");
	private JLabel lblEvent = new JLabel("<html>Mr. Supercalifraguilisticexpialidicious came across a caravan of a wealthy man as they traveled. The bored man asks for a joke in return for coins.<br><br>\r\nMr. Supercalifraguilisticexpialidicious receives 10 coins telling him about a chicken crossing the road.</html>");
	
	private JPanel battlePanel = new JPanel();
	private JButton btnAttack = new JButton("Attack");
	private JButton btnDefend = new JButton("Defend");
	private JButton btnEnd = new JButton("Run Away");		
	private JLabel lblenemyHP = new JLabel("HP: 5/10");
	private JLabel lblEnemyName = new JLabel("Slime");
	private JLabel lblEnemyAction = new JLabel("<html>The Slime attacks! Mr. Supercalifragilisticexpialidocious dodges.</html>");		
	private JLabel lblPlayerAction = new JLabel("<html>Mr. Supercalifragilisticexpialidocious attacks! The Slime takes 5 damage.</html>");		
	
	private JPanel endPanel = new JPanel();		
	private JLabel lblEnddesc = new JLabel("<html>This frame is used for both the victory and game over screen - the text above might display Victory! instead if the player won. This label here is used to describe what happened. (ex. Player was defeated by the Slime, and can't continue in their endeavors.)</html>");		
	private JLabel lblEndtop = new JLabel("Game Over.");		
	private JButton btnTitleScreen = new JButton("Title Screen");		
	
	private JPanel statusBar = new JPanel();		
	private JLabel lblCharacterName = new JLabel("Mr. Supercalifragilisticexpialidocious");		
	private JComboBox cboItems = new JComboBox();		
	private JButton btnUseItem = new JButton("Use Item");		
	private JLabel lblAtk = new JLabel("ATK: 5");		
	private JLabel lblDef = new JLabel("DEF: 5");		
	private JLabel lblSpd = new JLabel("SPD: 5");		
	private JLabel lblInt = new JLabel("INT: 5");		
	private Button btnUpgAtk = new Button("+");
	private Button btnUpgDef = new Button("+");		
	private Button btnUpgSpd = new Button("+");		
	private Button btnUpgInt = new Button("+");		
	private JLabel lblMoney = new JLabel("Coins: 100");		
	private JLabel lblHP = new JLabel("HP: 30/30");		
	private JLabel lblExp = new JLabel("EXP: 1000");		
	private JLabel lblWeapon = new JLabel("Weapon: Sword");		
	private JLabel lblArmor = new JLabel("Armor: Leather");		
	private JLabel lblItemDesc = new JLabel("<html><b>Potion</b>: Heals 25 HP upon use.</html>");
	
	private JMenuBar menuBar = new JMenuBar();		
	private JMenu mnGame = new JMenu("Game");		
	private JMenuItem mntmTitle = new JMenuItem("Go to Title");		
	private JMenuItem mntmHelp = new JMenuItem("Help");		
	private JMenuItem mntmExit = new JMenuItem("Exit");
	
	
	public static void main(String[] args) {
        new RPGFrame();
	}
	
	public RPGFrame() {
		super("Simple RPG Game");
		statusBar.setVisible(false);
		buildGUI();
		titleScreen();
	}
	
	private void buildGUI()
	{
		setVisible(false);
		setSize(480,420);
        setLocation((int)getToolkit().getScreenSize().getWidth()/2-254,(int)getToolkit().getScreenSize().getHeight()/2-310);
        setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Hitting the X will close the program safely.
		getContentPane().setBackground(new Color(30, 144, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{475, 0};
		gridBagLayout.rowHeights = new int[]{213, 83, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		
		//titlePanel
		
		titlePanel.setBackground(new Color(216, 191, 216));
		tabbedPane.addTab("Title", null, titlePanel, null);
		titlePanel.setLayout(null);
		
		
		lblTitle.setForeground(new Color(0, 0, 139));
		lblTitle.setBackground(new Color(248, 248, 255));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 23));
		lblTitle.setBounds(133, 11, 180, 29);
		titlePanel.add(lblTitle);
		
		lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubtitle.setForeground(new Color(0, 0, 139));
		lblSubtitle.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		lblSubtitle.setBackground(new Color(248, 248, 255));
		lblSubtitle.setBounds(37, 36, 394, 29);
		titlePanel.add(lblSubtitle);

		btnNewGame.setBounds(271, 100, 160, 23);
		titlePanel.add(btnNewGame);

		btnContinueGame.setBounds(271, 134, 160, 23);
		titlePanel.add(btnContinueGame);
		
		cboLoad.setModel(new DefaultComboBoxModel(new String[]{}));
		cboLoad.setBounds(118, 135, 143, 20);
		titlePanel.add(cboLoad);
		
		nameField.setBounds(118, 101, 143, 20);
		titlePanel.add(nameField);
		nameField.setColumns(10);
		
		lblStartName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStartName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStartName.setBounds(21, 103, 87, 14);
		titlePanel.add(lblStartName);

		lblLoadCharacter.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLoadCharacter.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoadCharacter.setBounds(10, 137, 98, 14);
		titlePanel.add(lblLoadCharacter);
		
		btnDeleteCharacter.setBounds(271, 158, 160, 23);
		titlePanel.add(btnDeleteCharacter);

		//townPanel
		
		townPanel.setBackground(new Color(189, 183, 107));
		tabbedPane.addTab("Town", null, townPanel, null);
		townPanel.setLayout(null);

		lblTownname.setForeground(new Color(0, 0, 0));
		lblTownname.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 15));
		lblTownname.setBounds(27, 11, 213, 19);
		townPanel.add(lblTownname);
		
		cboItemBuy.setModel(new DefaultComboBoxModel(new String[] {}));
		cboItemBuy.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cboItemBuy.setBounds(104, 82, 136, 22);
		townPanel.add(cboItemBuy);
		
		lblReminder.setHorizontalAlignment(SwingConstants.LEFT);
		lblReminder.setForeground(Color.BLACK);
		lblReminder.setFont(new Font("Verdana", Font.ITALIC, 11));
		lblReminder.setBounds(49, 30, 374, 45);
		townPanel.add(lblReminder);
		
		btnBuyItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuyItem.setBounds(250, 82, 89, 23);
		townPanel.add(btnBuyItem);
		
		lblBuyItemDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuyItemDesc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBuyItemDesc.setBounds(104, 104, 235, 75);
		townPanel.add(lblBuyItemDesc);
		
		btnSaveNow.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSaveNow.setBounds(153, 185, 101, 23);
		townPanel.add(btnSaveNow);
		
		btnNextTown.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNextTown.setBounds(264, 185, 195, 23);
		townPanel.add(btnNextTown);
		
		travelPanel.setBackground(new Color(144, 238, 144));
		tabbedPane.addTab("Travel", null, travelPanel, null);
		travelPanel.setLayout(null);
		
		pbDistance.setMaximum(30);
		pbDistance.setValue(15);
		pbDistance.setBounds(93, 170, 272, 24);
		travelPanel.add(pbDistance);
		
		lblDistance.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistance.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblDistance.setBounds(120, 147, 213, 24);
		travelPanel.add(lblDistance);
		
		btnMoveOn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnMoveOn.setBounds(185, 195, 89, 23);
		travelPanel.add(btnMoveOn);
		
		lblTraveling.setForeground(Color.BLACK);
		lblTraveling.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 15));
		lblTraveling.setBounds(10, 11, 230, 19);
		travelPanel.add(lblTraveling);
		
		lblEvent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEvent.setBounds(20, 31, 424, 115);
		travelPanel.add(lblEvent);
		
		//battlePanel
		
		battlePanel.setBackground(new Color(240, 128, 128));
		tabbedPane.addTab("Battle", null, battlePanel, null);
		battlePanel.setLayout(null);
		
		btnAttack.setBounds(53, 151, 111, 23);
		battlePanel.add(btnAttack);
		
		btnDefend.setBounds(174, 151, 111, 23);
		battlePanel.add(btnDefend);
		
		btnEnd.setBounds(295, 151, 111, 23);
		battlePanel.add(btnEnd);

		lblenemyHP.setHorizontalAlignment(SwingConstants.CENTER);
		lblenemyHP.setForeground(new Color(85, 107, 47));
		lblenemyHP.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
		lblenemyHP.setBounds(160, 22, 112, 23);
		battlePanel.add(lblenemyHP);

		lblEnemyName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnemyName.setFont(new Font("Bookman Old Style", Font.BOLD, 18));
		lblEnemyName.setBounds(53, 0, 328, 28);
		battlePanel.add(lblEnemyName);

		lblEnemyAction.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEnemyAction.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnemyAction.setBounds(30, 102, 405, 38);
		battlePanel.add(lblEnemyAction);

		lblPlayerAction.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPlayerAction.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerAction.setBounds(30, 53, 405, 38);
		battlePanel.add(lblPlayerAction);

		//endPanel
		
		endPanel.setBackground(new Color(135, 206, 250));
		tabbedPane.addTab("End", null, endPanel, null);
		endPanel.setLayout(null);

		lblEnddesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnddesc.setBounds(60, 49, 330, 104);
		endPanel.add(lblEnddesc);

		lblEndtop.setForeground(new Color(0, 0, 0));
		lblEndtop.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEndtop.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndtop.setBounds(121, 11, 191, 37);
		endPanel.add(lblEndtop);

		btnTitleScreen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnTitleScreen.setBounds(148, 170, 138, 23);
		endPanel.add(btnTitleScreen);

		//statusBar
		
		statusBar.setBackground(new Color(230, 230, 250));
		statusBar.setLayout(null);
		GridBagConstraints gbc_statusBar = new GridBagConstraints();
		gbc_statusBar.fill = GridBagConstraints.BOTH;
		gbc_statusBar.gridx = 0;
		gbc_statusBar.gridy = 1;
		getContentPane().add(statusBar, gbc_statusBar);

		lblCharacterName.setHorizontalAlignment(SwingConstants.LEFT);
		lblCharacterName.setFont(new Font("Bookman Old Style", Font.BOLD, 14));
		lblCharacterName.setBounds(10, 45, 328, 17);
		statusBar.add(lblCharacterName);

		cboItems.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cboItems.setModel(new DefaultComboBoxModel(new String[] {}));
		cboItems.setBounds(10, 14, 136, 20);
		statusBar.add(cboItems);

		btnUseItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnUseItem.setBounds(378, 13, 89, 23);
		statusBar.add(btnUseItem);

		lblAtk.setForeground(new Color(165, 42, 42));
		lblAtk.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		lblAtk.setBounds(20, 70, 55, 15);
		statusBar.add(lblAtk);

		lblDef.setForeground(new Color(85, 107, 47));
		lblDef.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		lblDef.setBounds(20, 91, 55, 15);
		statusBar.add(lblDef);

		lblSpd.setForeground(new Color(72, 61, 139));
		lblSpd.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		lblSpd.setBounds(151, 70, 60, 15);
		statusBar.add(lblSpd);

		lblInt.setForeground(new Color(0, 0, 128));
		lblInt.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		lblInt.setBounds(151, 91, 55, 15);
		statusBar.add(lblInt);

		btnUpgAtk.setFont(new Font("Dialog", Font.BOLD, 12));
		btnUpgAtk.setEnabled(false);
		btnUpgAtk.setBounds(82, 73, 20, 12);
		statusBar.add(btnUpgAtk);
		
		btnUpgDef.setFont(new Font("Dialog", Font.BOLD, 12));
		btnUpgDef.setEnabled(false);
		btnUpgDef.setBounds(82, 93, 20, 12);
		statusBar.add(btnUpgDef);

		btnUpgSpd.setFont(new Font("Dialog", Font.BOLD, 12));
		btnUpgSpd.setEnabled(false);
		btnUpgSpd.setBounds(214, 73, 20, 12);
		statusBar.add(btnUpgSpd);

		btnUpgInt.setFont(new Font("Dialog", Font.BOLD, 12));
		btnUpgInt.setEnabled(false);
		btnUpgInt.setBounds(214, 94, 20, 12);
		statusBar.add(btnUpgInt);

		lblMoney.setForeground(new Color(128, 128, 0));
		lblMoney.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		lblMoney.setBounds(388, 73, 85, 15);
		statusBar.add(lblMoney);

		lblHP.setForeground(new Color(85, 107, 47));
		lblHP.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		lblHP.setBounds(388, 54, 85, 15);
		statusBar.add(lblHP);

		lblExp.setForeground(new Color(75, 0, 130));
		lblExp.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		lblExp.setBounds(388, 94, 85, 15);
		statusBar.add(lblExp);

		lblWeapon.setForeground(new Color(128, 0, 128));
		lblWeapon.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		lblWeapon.setBounds(239, 70, 146, 15);
		statusBar.add(lblWeapon);

		lblArmor.setForeground(new Color(0, 128, 128));
		lblArmor.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		lblArmor.setBounds(239, 92, 147, 15);
		statusBar.add(lblArmor);

		lblItemDesc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblItemDesc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemDesc.setBounds(156, 6, 212, 36);
		statusBar.add(lblItemDesc);

		setJMenuBar(menuBar);

		menuBar.add(mnGame);

		mnGame.add(mntmTitle);

		mnGame.add(mntmHelp);

		mnGame.add(mntmExit);
		
		//title
		btnNewGame.addActionListener(this);
		btnContinueGame.addActionListener(this);
		btnDeleteCharacter.addActionListener(this);
		cboLoad.addActionListener(this);
		//town
		btnBuyItem.addActionListener(this);
		btnSaveNow.addActionListener(this);
		btnNextTown.addActionListener(this);
		btnMoveOn.addActionListener(this);
		cboItemBuy.addActionListener(this);
		//enemy
		btnAttack.addActionListener(this);
		btnDefend.addActionListener(this);
		btnEnd.addActionListener(this);
		//end
		btnTitleScreen.addActionListener(this);
		//status bar
		btnUseItem.addActionListener(this);
		btnUpgAtk.addActionListener(this);
		btnUpgDef.addActionListener(this);
		btnUpgSpd.addActionListener(this);
		btnUpgInt.addActionListener(this);
		cboItems.addActionListener(this);
		
		mntmTitle.addActionListener(this);
		mntmHelp.addActionListener(this);
		mntmExit.addActionListener(this);
		
		tabbedPane.setEnabled(false);
		tabbedPane.setFont(new Font("Arial", Font.PLAIN, 0));
		
		setVisible(true);
	}
	
	private void refreshStatus(int c)
	{ //there's no need to change everything unless int is 0 - waste of resources. The integer specifies what we need to change otherwise. Warning: bitfield.
		if((c & ST_NAME) > 0) //name refresh
		{
			lblCharacterName.setText(currentChar.name);
		}
		if((c & ST_ITEM) > 0) //item refresh
		{
			cboItems.setModel(new DefaultComboBoxModel(currentChar.itemNameList()));
			if(cboItems.getItemCount() > 0)
			{
				System.out.println(cboItems.getItemCount());
				btnUseItem.setEnabled(true);
				cboItems.setSelectedIndex(-1);
				cboItems.setSelectedIndex(0);
				Item selectedItem = currentChar.inventory.get(cboItems.getSelectedIndex());
				lblItemDesc.setText("<html><b>"+selectedItem.name+"</b> - "+selectedItem.desc);
			}
			else
			{
				btnUseItem.setEnabled(false);
				lblItemDesc.setText("");
			}
		}
		if((c & ST_HP) > 0) //hp refresh
		{
			lblHP.setText("HP: " + currentChar.HP+"/"+currentChar.maxHP);
		}
		if((c & ST_MONEY) > 0) //money refresh
		{
			lblMoney.setText("Coins: " + currentChar.money);
		}
		if((c & ST_EXP) > 0) //exp refresh
		{
			int exp = currentChar.exp;
			lblExp.setText("EXP: " + exp);
			if(exp >= 100)
			{
				btnUpgAtk.setEnabled(true);
				btnUpgDef.setEnabled(true);
				btnUpgSpd.setEnabled(true);
				btnUpgInt.setEnabled(true);
			}
			else 
			{
				btnUpgAtk.setEnabled(false);
				btnUpgDef.setEnabled(false);
				btnUpgSpd.setEnabled(false);
				btnUpgInt.setEnabled(false);
			}
		}
		if((c & ST_WEPATK) > 0) //weapon + attack refresh
		{
			if(currentChar.eqWeapon >= 0)
			{
				lblWeapon.setText("Weapon: " + currentChar.equippedWeapon.name);
			}
			else
			{
				lblWeapon.setText("Weapon: None");
			}
			lblAtk.setText("ATK: " + currentChar.getTotalAtk());
		}
		if((c & ST_ARMDEF) > 0) //armor + defense refresh
		{
			if(currentChar.eqArmor >= 0)
			{
				lblArmor.setText("Armor: " + currentChar.equippedArmor.name);
			}
			else
			{
				lblArmor.setText("Armor: None");
			}
			lblDef.setText("DEF: " + currentChar.getTotalDef());
		}
		if((c & ST_SPD) > 0) //speed refresh
		{
			lblSpd.setText("SPD: " + currentChar.speed);
		}
		if((c & ST_INT) > 0) //intelligence refresh
		{
			lblInt.setText("INT: " + currentChar.intelligence);
		}
	}
	
	private void titleScreen()
	{
		charList = Character.createCharacterArray();
		cboLoad.setModel(new DefaultComboBoxModel(charList[CHAR_NAME]));
		if(cboLoad.getItemCount() > 0) cboLoad.setSelectedIndex(0);
		statusBar.setVisible(false);
		tabbedPane.setSelectedIndex(0); //change later
	}
	
	private void enterTown(int townID)
	{
		curTown = new Town(townID);
		nextTown = new Town(townID + 1);
		lblTownname.setText("Welcome to " + curTown.name + "!");
		if(nextTown.exists()) btnNextTown.setText("Proceed to " + nextTown.name);
		else btnNextTown.setText("Proceed to The End.");
		cboItemBuy.setModel(new DefaultComboBoxModel(curTown.itemNameList()));
		if(cboItemBuy.getItemCount() > 0) {
			btnBuyItem.setEnabled(true);
			cboItemBuy.setSelectedIndex(0);
		Item selectedItem = curTown.items.get(cboItemBuy.getSelectedIndex());
		lblBuyItemDesc.setText("<html><b>" + selectedItem.name + "</b> - " + selectedItem.getTypeName() + " - "
				+ "" + selectedItem.price + " coins<br>" + selectedItem.desc + "</html>");
		}
		else
		{
			lblBuyItemDesc.setText("");
			btnBuyItem.setEnabled(false);
		}
		currentChar.HP = currentChar.maxHP;
		currentChar.atTown = curTown.ID;
		currentChar.saveCharacter();
		refreshStatus(ST_ALL);
		statusBar.setVisible(true);
		tabbedPane.setSelectedIndex(1); //change later
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object c = e.getSource();
		
		//title / main menu
		if(c == btnNewGame) //New Game button
		{
			if(nameField.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "Please type in a character name!");
			}
			else
			{
				String newName = nameField.getText();
				currentChar = new Character(newName, Character.findHighCharID() + 1);
				currentChar.newCharacter(); //add the new character to the database
				enterTown(currentChar.atTown);
			}
		}
		if(c == btnContinueGame) //Continue Game button
		{
			currentChar = new Character(Integer.parseInt(charList[CHAR_ID][cboLoad.getSelectedIndex()]));
			System.out.println("Continuing with character " + currentChar.name);
			enterTown(currentChar.atTown);
		}
		if(c == btnDeleteCharacter) //Delete Character button
		{
			Character.deleteCharacter(Integer.parseInt(charList[CHAR_ID][cboLoad.getSelectedIndex()]));
			charList = Character.createCharacterArray(); //after deletion, update the character list.
			cboLoad.setModel(new DefaultComboBoxModel(charList[CHAR_NAME]));
			if(cboLoad.getItemCount() > 0) cboLoad.setSelectedIndex(0);
		}
		if(c == cboLoad) //Combo box containing characters changes in selection
		{
			System.out.println("Load Combo Box - selected item: " + cboLoad.getSelectedItem());
		}
		
		//town
		if(c == btnBuyItem) //Buy Item
		{
			Item selectedItem = curTown.items.get(cboItemBuy.getSelectedIndex());
			if (currentChar.buyItem(selectedItem))
			{
				refreshStatus(ST_ITEM | ST_MONEY);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Not enough coins!");
			}
		}
		if(c == btnNextTown) //Next Town button, showing something like 'Proceed to Woodstown'
		{
			currentChar.saveCharacter();
			System.out.println("Next Town");
		}
		if(c == btnSaveNow) //Saves the character right now 
		{
			currentChar.saveCharacter();
		}
		if(c == cboItemBuy) //Item purchase combo box change in selection
		{
			System.out.println("Item Buy Combo Box - selected item: " + cboItemBuy.getSelectedItem());
			Item selectedItem = curTown.items.get(cboItemBuy.getSelectedIndex());
			lblBuyItemDesc.setText("<html><b>" + selectedItem.name + "</b> - " + selectedItem.getTypeName() + " - "
					+ "" + selectedItem.price + " coins<br>" + selectedItem.desc + "</html>");
		}
		
		//traveling
		if(c == btnMoveOn) //Move On button - advance one kilometer, trigger a possible event. Or if kilometers traveled equal to max kilometers, 
		{				   //the next press will bring them into the town. The button probably showing "Enter Town" at that point.
			System.out.println("Move On");
		}
		
		//enemy
		if(c == btnAttack) //Attack button. Will be grayed out when the battle ends.
		{
			System.out.println("Attack");
		}
		if(c == btnDefend) //Defend button. Will be grayed out when the battle ends.
		{
			System.out.println("Defend");
		}
		if(c == btnEnd) //This can either be shown as "Run away" or "Move on" - The first option is a random chance to escape back to traveling,
		{				//and the other is given when the player defeats the enemy.
			System.out.println("End Battle");
		}
		
		//end
		if(c == btnTitleScreen) //Button to go back to the title screen.
		{
			System.out.println("Title Screen");
		}
		
		//status bar
		if(c == btnUseItem) //Use Item button
		{
			int usetype = currentChar.useItem(cboItems.getSelectedIndex());
			if(usetype == 0) //healing was used
				refreshStatus(ST_ITEM | ST_HP);
			else if(usetype == 1) //weapon was used
				refreshStatus(ST_ITEM | ST_WEPATK);
			else if(usetype == 2) //armor was used
				refreshStatus(ST_ITEM | ST_ARMDEF);
			else if(usetype == 3) //EXP item was used
				refreshStatus(ST_ITEM | ST_EXP);
		}
		if(c == cboItems) //Selection made on player's list of items
		{
			if(cboItems.getItemCount() > 0)
			{
			System.out.println("Item Combo Box - selected item: " + cboItems.getSelectedItem());
			if(cboItems.getSelectedIndex() == -1) cboItems.setSelectedIndex(0);
			Item selectedItem = currentChar.inventory.get(cboItems.getSelectedIndex());
			lblItemDesc.setText("<html><b>"+selectedItem.name+"</b> - "+selectedItem.desc);
			}
		}
		
		//Upgrading: Spend 100 EXP per stat upgrade. Probably need a method to add/subtract EXP while simultaneously checking to see if EXP > 100 to enable the buttons.
		if(c == btnUpgAtk) //Upgrade attack power
		{
			System.out.println("Upgrade Attack");
		}
		if(c == btnUpgDef) //Upgrade defenses
		{
			System.out.println("Upgrade Defense");
		}
		if(c == btnUpgSpd) //Upgrade speed
		{
			System.out.println("Upgrade Speed");
		}
		if(c == btnUpgInt) //Upgrade intellect
		{
			System.out.println("Upgrade Intellect");
		}
		
		if(c == mntmTitle) //Menu option Title selected - this will make the player go back to the title screen, effectively quitting without exiting the program.
		{
			titleScreen();
		}
		if(c == mntmHelp) //How to Play thing - will bring up a window showing what the player should do.
		{
			System.out.println("Menu Help");
		}
		if(c == mntmExit) //Obviously quits the program.
		{
			System.exit(0);
		}
		
	}


}