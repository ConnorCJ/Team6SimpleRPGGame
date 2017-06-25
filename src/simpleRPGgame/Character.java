package simpleRPGgame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Character {
	
	public String name;
	public int ID,attack,defense,speed,intelligence,money,maxHP,atTown,eqWeapon,eqArmor,exp,HP;
	public Item equippedWeapon, equippedArmor;
	public ArrayList<Item> inventory;
	
	public Character(String name, int ID) {
		this.name = name;
		this.ID = ID;
		attack = defense = speed = intelligence = 5; 
		eqWeapon = eqArmor = -1;
		money = 50;
		maxHP = 10;
		atTown = 1;
		exp = 1000;
		equippedWeapon = null;
		equippedArmor = null;
		inventory = new ArrayList<Item>(10);
	}
	
	public Character(int charID){
		Connection con = SQLConnection.getConnection();
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			String sql;
			ResultSet rs = stmt.executeQuery(sql = "SELECT * FROM CHARACTER WHERE charID = '" + charID + "'");
			System.out.println(sql);
			
			this.name = rs.getString("Name");
			this.ID = rs.getInt("charID");
			this.attack = rs.getInt("Attack");
			this.defense = rs.getInt("Defense");
			this.speed = rs.getInt("Speed");
			this.intelligence = rs.getInt("Intelligence");
			this.money = rs.getInt("Money");
			this.maxHP = rs.getInt("MaxHP");
			this.atTown = rs.getInt("AtTown");
			this.eqWeapon = rs.getInt("EqWeapon");
			this.eqArmor = rs.getInt("EqArmor");
			this.exp = rs.getInt("EXP");
			inventory = getItemsOnChar();
			
			rs.close();
			stmt.close();
			con.close();
			
		}catch (SQLException ex){
			System.err.println(ex);
			ex.printStackTrace();
		}
		
		if(eqWeapon >= 0) equippedWeapon = new Item(eqWeapon);
		if(eqArmor >= 0) equippedArmor = new Item(eqArmor);
	}
	
	public ArrayList<Item> getItemsOnChar()
	{
		String sql = "SELECT Item.* FROM Item JOIN ItemOnChar ON Item.ItemID = ItemOnChar.ItemID WHERE CharID = " + Integer.toString(ID);
		System.out.println(sql);
		Connection con = SQLConnection.getConnection();
		ArrayList<Item> i = new ArrayList<Item>(20);
		
		try
    	{
    	con.setAutoCommit(false);
   	 	Statement stmt = con.createStatement();
   	 	ResultSet rs    = stmt.executeQuery(sql);
        
        // loop through the result set
        while (rs.next()) 
        	{
            i.add(new Item(
            		rs.getInt("ItemID"),
            		rs.getString("ItemName"),
            		rs.getString("ItemDesc"),
            		rs.getInt("Value"),
            		rs.getInt("ItemType"),
            		rs.getInt("Price"))
            		);
        	}
		rs.close();
		stmt.close();
		con.close();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
		
		return i;
	}
	
	public String[] itemNameList() //for placement in a cbolist
	{
		String[] names = new String[inventory.size()]; 
		
		for(int i = 0; i < inventory.size(); ++i)
		{
			names[i] = inventory.get(i).name;
		}
		
		return names;
	}
	
	public boolean buyItem(Item item)
	{
		if(money >= item.price)
		{
			money -= item.price;
			inventory.add(item);
			return true;
		}
		else return false;
	}
	
	public int useItem(int index)
	{
		Item useItem = inventory.get(index);
		int iType = useItem.type;
		if(iType == 0) //healing item
		{
			heal(useItem.value);
		}
		else if(iType == 1) //weapon equipment
		{
			if(eqWeapon >= 0)
			{
				inventory.add(equippedWeapon);
			}
			equippedWeapon = useItem;
			eqWeapon = equippedWeapon.ID;
		}
		else if(iType == 2) //armor equipment
		{
			if(eqArmor >= 0)
			{
				inventory.add(equippedArmor);
			}
			equippedArmor = useItem;
			eqArmor = equippedArmor.ID;
		}
		else if(iType == 3) //give EXP
		{
			exp += useItem.value;
		}
		
		inventory.remove(index);
		return iType;
	}
	
	public int getTotalAtk()
	{
		if(eqWeapon >= 0) return attack + equippedWeapon.value;
		else return attack;
	}
	
	public int getTotalDef()
	{
		if(eqArmor >= 0) return defense + equippedArmor.value;
		else return defense;
	}
	
	public int getEXP(){
		return exp;
	}
	
	public void decreaseEXP(){
		this.exp = exp - 50;
	}
	
	public void increaseAttack(){
		this.attack += 1; 
		this.maxHP += 2;
		this.HP += 2;
	}
	public void increaseDef(){
		this.defense += 1;
		this.maxHP += 2;
		this.HP += 2;
	}
	public void increaseSpeed(){
		this.speed += 1;
		this.maxHP += 2;
		this.HP += 2;
	}
	public void increaseIntel(){
		this.intelligence += 1;
		this.maxHP += 2;
		this.HP += 2;
	}
	
	public boolean damage(int dmg) //subtract HP from character without going below 0. returns true if character reaches 0.
	{
		HP -= dmg;
		if(HP < 0) HP = 0;
		return HP <= 0;
	}
	
	public void heal(int hl) //adds HP without going above maxHP
	{
		HP += hl;
		if(HP > maxHP) HP = maxHP;
	}
	
	public void loseMoney(int amount)
	{
		money -= amount;
		if(money < 0) money = 0;
	}
	
	public static int findHighCharID(){ //fun fact about the "static" definer: You can use this function without instantiating a Character.
		
		Connection con = SQLConnection.getConnection();

		int high = 0;
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			String sql;
			ResultSet rs = stmt.executeQuery(sql = "SELECT * FROM CHARACTER;");
			System.out.println(sql);
			
			while(rs.next()){
				high = rs.getInt("CharID");
			}
			stmt.close();
			con.close();
			
		}catch (SQLException ex) {
			System.out.println(ex);
		}
		
		return high;
	}
	
	public static String[][] createCharacterArray(){
		
		Connection con = SQLConnection.getConnection();
		String[][] listOfCharacters;
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			String sql;
			ResultSet rs = stmt.executeQuery(sql = "SELECT COUNT(CharID) AS 'Check' FROM Character;");
			System.out.println(sql);
			listOfCharacters = new String[2][rs.getInt("Check")];
			rs = stmt.executeQuery(sql = "SELECT * FROM CHARACTER;");
			System.out.println(sql);
			int i = 0;
			while(rs.next()){
				String name = rs.getString("Name");
				int ID = rs.getInt("CharID");
				listOfCharacters[0][i] = Integer.toString(ID);
				listOfCharacters[1][i] = name;
				++i;
			}
			stmt.close();
			rs.close();
			con.close();
			
		}catch (SQLException ex) {
			listOfCharacters = new String[2][1];
			listOfCharacters[0][0] = "0";
			listOfCharacters[1][0] = "Error! Do not load!";
			System.err.println(ex);
			ex.printStackTrace();
		}
		return listOfCharacters;
	}
	
	public void newCharacter(){
		Connection con = SQLConnection.getConnection();
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			String rs = ("INSERT INTO Character (CharID,Name,Attack,Defense,Speed,Intelligence,Money,MaxHP,ATTOWN,EQWEAPON,EQARMOR,EXP) " 
			+ "VALUES ("+ ID +", '" + name + "', "+ attack + ", " + defense + ", " + speed + ", " +
					intelligence + ", " + money + ", " + maxHP + ", " + atTown + ", " + 
					eqWeapon + ", " + eqArmor + ", " + exp + ");");
			
			System.out.println(rs);
			stmt.executeUpdate(rs);
			stmt.close();
			con.commit();
			con.close();
			
		}catch (SQLException ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
	}
	
	public static void deleteCharacter(int charID)
	{
		Connection con = SQLConnection.getConnection();
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			String rs = "DELETE FROM Character WHERE CharID="+ charID + ";" +
					"DELETE FROM ItemOnChar WHERE CharID=" + charID +";";
			System.out.println(rs);
			stmt.executeUpdate(rs);
			stmt.close();
			con.commit();
			con.close();
			System.out.println("Character ID " + charID + " deleted from database");
			
		}catch (SQLException ex) {
			System.out.println(ex);
		}
	}
	
	public void saveCharacter(){
		Connection con = SQLConnection.getConnection();
		
		try{
			String sql;
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			System.out.println(sql = "UPDATE CHARACTER SET Attack =" + attack + ", Defense = " + defense + ", Speed =" + speed +
					", Intelligence =" + intelligence + ", Money =" + money + ", atTown=" + atTown + ", eqWeapon =" + eqWeapon +
					", eqArmor =" + eqArmor + ", EXP =" + exp + ", MaxHP =" + maxHP + " where CharID =" + ID + ";" +
					"DELETE FROM ItemOnChar WHERE CharID=" + ID +";"); 
			stmt.executeUpdate(sql);
			String itemlist = "";
			for(int i = 0; i < inventory.size(); ++i)
				itemlist += "INSERT INTO ItemOnChar VALUES ("+ID+","+inventory.get(i).ID+");";
			if(!(itemlist.equals("")))
			{
				System.out.println(itemlist);
				stmt.executeUpdate(itemlist);
			}
				
			
			stmt.close();
			con.commit();
			con.close();
			
		}catch (SQLException ex) {
			System.err.println(ex);
			ex.printStackTrace();
		}
	}
	
	public String toString()
	{
		return name;
	}
}