package simpleRPGgame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Character {
	
	String name;
	int charID,attack,defense,speed,intelligence,money,maxHP,atTown,eqWeapon,eqArmor,exp;
	
	public Character(String name, int charID){
		this.name = name;
		this.charID = charID;
		attack = defense = speed = intelligence = eqWeapon = eqArmor = 5;
		money = 50;
		maxHP = 10;
		atTown = 1;
		exp = 1000;
		
	}
	
	public Character(String charName){
		Connection con = SQLConnection.getConnection();
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CHARACTER WHERE Name = '" + charName + "'");
			
			this.name = charName;
			this.charID = rs.getInt("CharID");
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
			
			rs.close();
			stmt.close();
			con.close();
			
		}catch (SQLException ex){
			System.out.println(ex);
		}
		
	}
	
	public void saveCharacter(){
		Connection con = SQLConnection.getConnection();
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE CHARACTER set attack =" + attack + ", defense=" + defense + ", speed=" + speed +
										", intelligence=" + intelligence + ", money=" + money + ", atTown=" + (atTown+1) + ", eqWeapon=" + eqWeapon +
										", eqArmor=" + eqArmor + ", exp=" + exp + " where CHARID =" + charID + ";");
			
			stmt.close();
			con.commit();
			con.close();
			
		}catch (SQLException ex) {
			System.out.println(ex);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCharID() {
		return charID;
	}

	public void setCharID(int charID) {
		this.charID = charID;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getAtTown() {
		return atTown;
	}

	public void setAtTown(int atTown) {
		this.atTown = atTown;
	}

	public int getEqWeapon() {
		return eqWeapon;
	}

	public void setEqWeapon(int eqWeapon) {
		this.eqWeapon = eqWeapon;
	}

	public int getEqArmor() {
		return eqArmor;
	}

	public void setEqArmor(int eqArmor) {
		this.eqArmor = eqArmor;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
}
