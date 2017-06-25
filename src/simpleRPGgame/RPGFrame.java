package simpleRPGgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RPGFrame extends JFrame implements ActionListener {
	int current_charID = 1;
	Character curChar;
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
	
	Adventuring ad;
	
	boolean playerDefeated = false;
	boolean enemyEncounter = false;

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
		btnMoveOn.setBounds(162, 195, 138, 23);
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
		mntmExit.addActionListener(this);
		
		tabbedPane.setEnabled(false);
		tabbedPane.setFont(new Font("Arial", Font.PLAIN, 0));
		
		setVisible(true);
	}
	
	private void refreshStatus(int c)
	{ //there's no need to change everything unless int is 0 - waste of resources. The integer specifies what we need to change otherwise. Warning: bitfield.
		if((c & ST_NAME) > 0) //name refresh
		{
			lblCharacterName.setText(curChar.name);
		}
		if((c & ST_ITEM) > 0) //item refresh
		{
			cboItems.setModel(new DefaultComboBoxModel(curChar.itemNameList()));
			if(cboItems.getItemCount() > 0)
			{
				btnUseItem.setEnabled(true);
				cboItems.setSelectedIndex(-1);
				cboItems.setSelectedIndex(0);
				Item selectedItem = curChar.inventory.get(cboItems.getSelectedIndex());
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
			lblHP.setText("HP: " + curChar.HP+"/"+curChar.maxHP);
		}
		if((c & ST_MONEY) > 0) //money refresh
		{
			lblMoney.setText("Coins: " + curChar.money);
		}
		if((c & ST_EXP) > 0) //exp refresh
		{
			int exp = curChar.exp;
			lblExp.setText("EXP: " + exp);
			if(exp >= 50)
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
			if(curChar.eqWeapon >= 0)
			{
				lblWeapon.setText("Weapon: " + curChar.equippedWeapon.name);
			}
			else
			{
				lblWeapon.setText("Weapon: None");
			}
			lblAtk.setText("ATK: " + curChar.getTotalAtk());
		}
		if((c & ST_ARMDEF) > 0) //armor + defense refresh
		{
			if(curChar.eqArmor >= 0)
			{
				lblArmor.setText("Armor: " + curChar.equippedArmor.name);
			}
			else
			{
				lblArmor.setText("Armor: None");
			}
			lblDef.setText("DEF: " + curChar.getTotalDef());
		}
		if((c & ST_SPD) > 0) //speed refresh
		{
			lblSpd.setText("SPD: " + curChar.speed);
		}
		if((c & ST_INT) > 0) //intelligence refresh
		{
			lblInt.setText("INT: " + curChar.intelligence);
		}
	}
	
	private void titleScreen()
	{
		charList = Character.createCharacterArray();
		cboLoad.setModel(new DefaultComboBoxModel(charList[CHAR_NAME]));
		if(cboLoad.getItemCount() > 0) cboLoad.setSelectedIndex(0);
		statusBar.setVisible(false);
		tabbedPane.setSelectedIndex(0); //change later
		playerDefeated = false;
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
		curChar.HP = curChar.maxHP;
		curChar.atTown = curTown.ID;
		curChar.saveCharacter();
		refreshStatus(ST_ALL);
		statusBar.setVisible(true);
		tabbedPane.setSelectedIndex(1); //change later
	}
	
	private void enterTraveling() //called when starting to enter traveling
	{
		ad = new Adventuring(curTown.ID);
		if(nextTown.exists()) {
			lblTraveling.setText("Traveling to " + nextTown.name + "...");
			lblDistance.setText(ad.distRemaining() + " kilometers left to " + nextTown.name);
		}
		else {
			lblTraveling.setText("Traveling to the end...");
			lblDistance.setText(ad.distRemaining() + " kilometers left to The End");
			ad.lastPath = true;
		}
		lblEvent.setText("<html>" + curChar.name + " sets out for the next location.<br><br>(Click 'Move on' to travel and encounter events along the way.)</html>");
		pbDistance.setMaximum(ad.maxDist);
		pbDistance.setValue(ad.dist);
		btnMoveOn.setText("Move on");
		tabbedPane.setSelectedIndex(2);
	}
	
	private void returnTraveling(boolean escaped) //called after defeating a foe
	{
		if(escaped) lblEvent.setText("<html>"+ curChar.name + " had ran away from their foe.</html>");
			else lblEvent.setText("<html>"+ curChar.name + " has defeated their foe, and is ready to move on.</html>");
		btnMoveOn.setText("Move on");
		tabbedPane.setSelectedIndex(2);
	}
	
	private void encounterEnemy()
	{
		ad.foe.HP = ad.foe.maxHP;
		lblEnemyName.setText(ad.foe.name);
		lblenemyHP.setText("HP: " + ad.foe.HP + "/" + ad.foe.maxHP);
		lblPlayerAction.setText("<html>" + curChar.name + " readies for a fight.</html>");
		lblEnemyAction.setText("<html>" + ad.foe.name + " draws near!");
		btnAttack.setEnabled(true);
		btnDefend.setEnabled(true);
		if(ad.foe.isBoss) {
			btnEnd.setEnabled(false);
			btnEnd.setText("Defeat boss!");
		}
		else {
			btnEnd.setEnabled(true);
			btnEnd.setText("Run Away");
		}
		
		tabbedPane.setSelectedIndex(3);
	}
	
	private void endBattle(String msg)
	{
		btnAttack.setEnabled(false);
		btnDefend.setEnabled(false);
		btnEnd.setEnabled(true);
		btnEnd.setText(msg);
	}
	
	private void enterEnd(boolean won, String msg)
	{
		if(won) lblEndtop.setText("Victory!");
		else lblEndtop.setText("Game over.");
		
		lblEnddesc.setText("<html>"+msg+"</html>");
		
		tabbedPane.setSelectedIndex(4);
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
				curChar = new Character(newName, Character.findHighCharID() + 1);
				curChar.newCharacter(); //add the new character to the database
				enterTown(curChar.atTown);
			}
			return;
		}
		if(c == btnContinueGame) //Continue Game button
		{
			curChar = new Character(Integer.parseInt(charList[CHAR_ID][cboLoad.getSelectedIndex()]));
			enterTown(curChar.atTown);
			return;
		}
		if(c == btnDeleteCharacter) //Delete Character button
		{
			Character.deleteCharacter(Integer.parseInt(charList[CHAR_ID][cboLoad.getSelectedIndex()]));
			charList = Character.createCharacterArray(); //after deletion, update the character list.
			cboLoad.setModel(new DefaultComboBoxModel(charList[CHAR_NAME]));
			if(cboLoad.getItemCount() > 0) cboLoad.setSelectedIndex(0);
			return;
		}
		if(c == cboLoad) //Combo box containing characters changes in selection
		{
			return;
		}
		
		//town
		if(c == btnBuyItem) //Buy Item
		{
			Item selectedItem = curTown.items.get(cboItemBuy.getSelectedIndex());
			if (curChar.buyItem(selectedItem))
			{
				refreshStatus(ST_ITEM | ST_MONEY);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Not enough coins!");
			}
			return;
		}
		if(c == btnNextTown) //Next Town button, showing something like 'Proceed to Woodstown'
		{
			curChar.saveCharacter();
			enterTraveling();
			return;
		}
		if(c == btnSaveNow) //Saves the character right now 
		{
			curChar.saveCharacter();
			return;
		}
		if(c == cboItemBuy) //Item purchase combo box change in selection
		{
			Item selectedItem = curTown.items.get(cboItemBuy.getSelectedIndex());
			lblBuyItemDesc.setText("<html><b>" + selectedItem.name + "</b> - " + selectedItem.getTypeName() + " - "
					+ "" + selectedItem.price + " coins<br>" + selectedItem.desc + "</html>");
			return;
		}
		
		//traveling
		if(c == btnMoveOn) //Move On button - advance one kilometer, trigger a possible event. Or if kilometers traveled equal to max kilometers, 
		{				   //the next press will bring them into the town. The button probably showing "Enter Town" at that point.
			
			if(playerDefeated)
			{
				enterEnd(false,curChar.name + " could not endure the harsh environment around them, and thus, was unable to continue.");
				return;
			}
			
			if(ad.arrived)
			{
				if(ad.lastPath)
				{
					ad.summonBoss();
					encounterEnemy();
				}
				else
				{
					enterTown(nextTown.ID);
				}
				return;
			}
			
			if(enemyEncounter)
			{
				ad.summonEnemy();
				encounterEnemy();
				enemyEncounter = false;
				return;
			}
			
			ad.advance();
			enemyEncounter = (ad.ev.type == 1);
			pbDistance.setValue(ad.dist);
			if(nextTown.exists()) {
				lblDistance.setText(ad.distRemaining() + " kilometers left to " + nextTown.name);
			}
			else {
				lblDistance.setText(ad.distRemaining() + " kilometers left to The End");
			}
			
			
			if(ad.dist == ad.maxDist)
			{
				ad.arrived = true;
				if(ad.lastPath)
				{
					lblEvent.setText("<html>" + curChar.name + 
							" has reached the end.<br><br>There before them, awaits the final foe. They are ready.<br><br>The fight begins.</html>");
					btnMoveOn.setText("Fight!");
				}
				else
				{
					lblEvent.setText("<html>" + curChar.name + " has arrived at " + nextTown.name + "!</html>");
					btnMoveOn.setText("Enter town");
					enemyEncounter = false;
				}
				return;
			}
			
			String eventMes = "<html>" + ad.ev.desc + "<br><br>";
			if(ad.ev.winEvent(curChar)) 
			{
				
				eventMes += ad.ev.sucdesc + "</html>";
				if(ad.ev.type == 1) //monster encounter
				{
					btnMoveOn.setText("Fight!");
				}
				else if(ad.ev.type == 2)
				{
					curChar.money += ad.ev.value;
					refreshStatus(ST_MONEY);
				}
				else if(ad.ev.type == 4)
				{
					curChar.exp += ad.ev.value;
					refreshStatus(ST_EXP);
				}
				else if(ad.ev.type == 5)
				{
					curChar.heal(ad.ev.value);
					refreshStatus(ST_HP);
				}
				else if(ad.ev.type == 7)
				{
					curChar.inventory.add(new Item(ad.ev.value));
					refreshStatus(ST_ITEM);
				}
			}
			else
			{
				eventMes += ad.ev.faildesc + "</html>";
				if(ad.ev.type == 3)
				{
					curChar.loseMoney(ad.ev.value);
					refreshStatus(ST_MONEY);
				}
				if(ad.ev.type == 6)
				{
					if(curChar.damage(ad.ev.value)) //possible to die this way. If true, it means HP reached 0.
					{
						playerDefeated = true;
						btnMoveOn.setText("Game Over!");
						btnUseItem.setEnabled(false); //no, you're still dead even if you were able to use an item.
					}
					refreshStatus(ST_HP);
				}
			}
			
			eventMes = eventMes.replaceAll("&n", curChar.name);
			
			lblEvent.setText(eventMes);
			
			
			return;
		}
		
		//enemy
		if(c == btnAttack) //Attack button. Will be grayed out when the battle ends.
		{
			Damage dmg = ad.calcPlrDamage(curChar);
			boolean enemyDefeat = false;
			String mes = "<html>" + curChar.name;
			if((dmg.flags & Damage.DMG_DEFBONUS) > 0)
				mes += " counterattacks! ";
			else mes += " attacks! ";
			
			if((dmg.flags & Damage.DMG_DODGE) > 0)
			{
				mes += ad.foe.name + " dodges.</html>";
			}
			else
			{
				if((dmg.flags & Damage.DMG_CRIT) > 0)
					mes += "Critical hit! ";
				
				mes += ad.foe.name + " takes " + dmg.damage + " damage.</html>";
				enemyDefeat = ad.foe.damage(dmg.damage); //detects if the enemy is defeated in this move or not as well.
				lblenemyHP.setText("HP: " + ad.foe.HP + "/" + ad.foe.maxHP);
			}
			lblPlayerAction.setText(mes);
			
			mes = "<html>" + ad.foe.name;
			if(enemyDefeat)
			{
				mes += " has been defeated. ";
				if(ad.foe.isBoss)
				{
					mes += " It's all over. Victory belongs to " + curChar.name + ".</html>";
					curChar.exp += ad.foe.exp;
					curChar.money += ad.foe.exp;
					endBattle("Victory!");
				}
				else
				{
					mes += ad.foe.exp + " EXP, " + ad.foe.money + " coins gained.</html>";
					curChar.exp += ad.foe.exp;
					curChar.money += ad.foe.exp;
					refreshStatus(ST_EXP | ST_MONEY);
					endBattle("Continue");
				}
			}
			else
			{
				dmg = ad.calcEnemyDamage(curChar);
				mes += " attacks! ";
				
				if((dmg.flags & Damage.DMG_DODGE) > 0)
				{
					mes += curChar.name + " dodges.</html>";
				}
				else
				{
					if((dmg.flags & Damage.DMG_CRIT) > 0)
						mes += "Critical hit! ";
					if((dmg.flags & Damage.DMG_DEFBONUS) > 0)
						mes += curChar.name + ", guarded, takes " + dmg.damage + " damage.";
					else mes += curChar.name + " takes " + dmg.damage + " damage.";
					if(playerDefeated = curChar.damage(dmg.damage))
					{
						mes += " " + curChar.name + " has been defeated!</html>";
						endBattle("Game Over");
					}
					else
					{
						mes += "</html>";
					}
					refreshStatus(ST_HP);
				}
				
				
			}
			
			lblEnemyAction.setText(mes);
			return;
		}
		if(c == btnDefend) //Defend button. Will be grayed out when the battle ends.
		{
			ad.plrDefended = true;
			lblPlayerAction.setText("<html>" + curChar.name + " defends and prepares...</html>");
			
			String mes = "<html>" + ad.foe.name;
				Damage dmg = ad.calcEnemyDamage(curChar);
				mes += " attacks! ";
				
				if((dmg.flags & Damage.DMG_DODGE) > 0)
				{
					mes += curChar.name + " dodges.</html>";
				}
				else
				{
					if((dmg.flags & Damage.DMG_CRIT) > 0)
						mes += "Critical hit! ";
					if((dmg.flags & Damage.DMG_DEFBONUS) > 0)
						mes += curChar.name + ", guarded, takes " + dmg.damage + " damage.";
					else mes += curChar.name + " takes " + dmg.damage + " damage.";
					if(playerDefeated = curChar.damage(dmg.damage))
					{
						mes += curChar.name + " has been defeated!</html>";
						endBattle("Game Over");
					}
					else
					{
						mes += "</html>";
					}
					refreshStatus(ST_HP);
				}
				lblEnemyAction.setText(mes);
			return;
		}
		if(c == btnEnd) //This can either be shown as "Run away" or "Move on" - The first option is a random chance to escape back to traveling,
		{				//and the other is given when the player defeats the enemy.
			
			if(playerDefeated)
			{
				enterEnd(false,curChar.name + " was defeated by " + ad.foe.name + ", and thus can't continue in their endeavors.");
			}
			else if(ad.foe.HP <= 0)
			{
				if(ad.foe.isBoss)
				{
					enterEnd(true,curChar.name + " has defeated the evil foe " + ad.foe.name + 
							". Their victory shall be remembered for days to come by the people of this simple RPG.");
				}
				else
				{
					returnTraveling(false);
				}
			}
			else if(ad.plrEscaped)
			{
				returnTraveling(true);
			}
			else if(ad.plrRun(curChar))
			{
				endBattle("Continue");
				lblPlayerAction.setText("<html>" + curChar.name + " attempts to run away! They are successful!</html>");
				lblEnemyAction.setText("<html>" + ad.foe.name + " could not catch up.</html>");
			}
			else
			{
				lblPlayerAction.setText("<html>" + curChar.name + " attempts to run away! They failed.</html>");
				String mes = "<html>" + ad.foe.name;
				Damage dmg = ad.calcEnemyDamage(curChar);
				mes += " attacks! ";
				
				if((dmg.flags & Damage.DMG_DODGE) > 0)
				{
					mes += curChar.name + " dodges.</html>";
				}
				else
				{
					if((dmg.flags & Damage.DMG_CRIT) > 0)
						mes += "Critical hit! ";
					if((dmg.flags & Damage.DMG_DEFBONUS) > 0)
						mes += curChar.name + ", guarded, takes " + dmg.damage + " damage.";
					else mes += curChar.name + " takes " + dmg.damage + " damage.";
					if(playerDefeated = curChar.damage(dmg.damage))
					{
						mes += curChar.name + " has been defeated!</html>";
						endBattle("Game Over");
					}
					else
					{
						mes += "</html>";
					}
					refreshStatus(ST_HP);
				}
				lblEnemyAction.setText(mes);
			}
			return;
		}
		
		//end
		if(c == btnTitleScreen) //Button to go back to the title screen.
		{
			titleScreen();
			return;
		}
		
		//status bar
		if(c == btnUseItem) //Use Item button
		{
			int usetype = curChar.useItem(cboItems.getSelectedIndex());
			if(usetype == 0) //healing was used
				refreshStatus(ST_ITEM | ST_HP);
			else if(usetype == 1) //weapon was used
				refreshStatus(ST_ITEM | ST_WEPATK);
			else if(usetype == 2) //armor was used
				refreshStatus(ST_ITEM | ST_ARMDEF);
			else if(usetype == 3) //EXP item was used
				refreshStatus(ST_ITEM | ST_EXP);
			return;
		}
		if(c == cboItems) //Selection made on player's list of items
		{
			if(cboItems.getItemCount() > 0)
			{
				if(cboItems.getSelectedIndex() == -1) cboItems.setSelectedIndex(0);
				Item selectedItem = curChar.inventory.get(cboItems.getSelectedIndex());
				lblItemDesc.setText("<html><b>"+selectedItem.name+"</b> - "+selectedItem.desc);
			}
			return;
		}
		
		//Upgrading: Spend 100 EXP per stat upgrade. Probably need a method to add/subtract EXP while simultaneously checking to see if EXP > 100 to enable the buttons.
		if(c == btnUpgAtk) //Upgrade attack power
		{
			if(!checkEXP(curChar))
				JOptionPane.showMessageDialog(this, "50 EXP Required!");
				
			else{
				curChar.increaseAttack();
				curChar.decreaseEXP();
				refreshStatus(ST_EXP | ST_HP | ST_WEPATK);
			}
			return;
		}
		if(c == btnUpgDef) //Upgrade defenses
		{
			if(!checkEXP(curChar))
				JOptionPane.showMessageDialog(this, "50 EXP Required!");
			
			else{
				curChar.increaseDef();
				curChar.decreaseEXP();
				refreshStatus(ST_EXP | ST_HP | ST_ARMDEF);
			}
			return;
		}
		if(c == btnUpgSpd) //Upgrade speed
		{
			if(!checkEXP(curChar))
				JOptionPane.showMessageDialog(this, "50 EXP Required!");
			else{
				curChar.increaseSpeed();
				curChar.decreaseEXP();
				refreshStatus(ST_EXP | ST_HP | ST_SPD);
			}
			return;
		}
		if(c == btnUpgInt) //Upgrade intellect
		{
			if(!checkEXP(curChar))
				JOptionPane.showMessageDialog(this, "50 EXP Required!");
			else{
				curChar.increaseIntel();
				curChar.decreaseEXP();
				refreshStatus(ST_EXP | ST_HP | ST_INT);
			}
			return;
		}
		
		if(c == mntmTitle) //Menu option Title selected - this will make the player go back to the title screen, effectively quitting without exiting the program.
		{
			titleScreen();
			return;
		}
		if(c == mntmExit) //Obviously quits the program.
		{
			System.exit(0);
			return;
		}
		
	}
	
	public boolean checkEXP(Character c){
		if(c.getEXP() >= 50)
			return true;
		return false;
	}

}