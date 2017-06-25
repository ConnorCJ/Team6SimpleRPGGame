package simpleRPGgame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Enemy {
	public int ID, maxHP, HP, attack, defense, speed, intelligence, exp, money;
	public String name;
	public boolean isBoss;
	
	
	public Enemy(int enemyID) //get event based off of given ID
	{
		Connection con = SQLConnection.getConnection();
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			String sql;
			ResultSet rs = stmt.executeQuery(sql = "SELECT * FROM Event WHERE enemyID = " + enemyID + ";");
			System.out.println(sql);
			ID = rs.getInt("EnemyID");
			maxHP = rs.getInt("MaxHP");
			isBoss = rs.getInt("isBoss") >= 1;
			attack = rs.getInt("Attack");
			defense = rs.getInt("Defense");
			speed = rs.getInt("Speed");
			intelligence = rs.getInt("Intelligence");
			exp = rs.getInt("EXP");
			money = rs.getInt("Money");
			name = rs.getString("Name");
			
			rs.close();
			stmt.close();
			con.close();
			
		}catch (SQLException ex){
			System.err.println(ex);
			ex.printStackTrace();
		}
		
		HP = maxHP;
	}
	
	public Enemy(int ID, int maxHP, int isBoss, int attack, int defense, int speed, int intelligence, int exp, int money, String name)
	{
		this.ID = ID;
		this.maxHP = maxHP;
		this.isBoss = isBoss >= 1;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
		this.intelligence = intelligence;
		this.exp = exp;
		this.money = money;
		this.name = name;
		HP = this.maxHP;
	}
	
	public boolean damage(int dmg) //subtract HP from enemy without going below 0. returns true if enemy reaches 0.
	{
		HP -= dmg;
		if(HP < 0) HP = 0;
		return HP <= 0;
	}
	
	public String toString()
	{
		return name;
	}
}
