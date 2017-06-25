package simpleRPGgame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Adventuring {

	ArrayList<Event> events;
	ArrayList<Enemy> enemies;
	public int dist, maxDist; 
	public boolean lastPath; //this the path to the final boss?
	public boolean arrived;
	public Enemy foe;
	public Event ev;
	public boolean plrDefended; //the player defended at one point, and thus gets bonuses.
	public boolean plrEscaped;
	
	public Adventuring(int townID) //get all the events from a townID, as well as the enemies.
	{
		events = new ArrayList<Event>(10);
		enemies = new ArrayList<Enemy>(10);
		Connection con = SQLConnection.getConnection();

        try
        	{
        	con.setAutoCommit(false);
       	 	Statement stmt = con.createStatement();
       	 	String sql;
       	 	ResultSet rs = stmt.executeQuery(sql = "SELECT * FROM Event WHERE EventTownID = " + Integer.toString(townID));
       	 	System.out.println(sql);
                // loop through the result set
            while (rs.next()) 
            {
               events.add(new Event(
               rs.getInt("EventID"),
               rs.getInt("EventStat"),	
               rs.getInt("EventType"),
               rs.getInt("EventValue"),
               rs.getInt("EventThreshold"),
               rs.getInt("EventTownID"),
               rs.getString("EventDesc"),
               rs.getString("EventSucDesc"),
               rs.getString("EventFailDesc")));
            }
                
            rs = stmt.executeQuery(sql = "SELECT * FROM Enemy WHERE TownID = " + Integer.toString(townID) + " AND isBoss = 0");
            System.out.println(sql);
            while (rs.next()) 
        	{
            enemies.add(new Enemy(
            		rs.getInt("EnemyID"), 
            		rs.getInt("MaxHP"), 
            		rs.getInt("isBoss"), 
            		rs.getInt("Attack"), 
            		rs.getInt("Defense"), 
            		rs.getInt("Speed"), 
            		rs.getInt("Intelligence"), 
            		rs.getInt("EXP"), 
            		rs.getInt("Money"), 
            		rs.getString("Name")));
        	}
			rs.close();
			stmt.close();
			con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        ev = new Event();
        dist = 0;
        maxDist = 30;
        plrDefended = false;
        arrived = false;
	}
	
	public int distRemaining()
	{
		return maxDist - dist;
	}
	
	public Event advance()
	{
		Random rand = new Random();
		++dist;
		int chance = rand.nextInt(5); //a 1 in 5 chance nothing happens 
		if(chance == 0)
		{
			ev = new Event();
			return ev;
		}
		else
		{
			chance = rand.nextInt(events.size()); //pick event at random
			ev = events.get(chance);
			return ev;
		}
	}
	
	public Enemy summonEnemy() //pick enemy at random for player to fight, return it
	{
		Random rand = new Random();
		foe = enemies.get(rand.nextInt(enemies.size()));
		return foe;
	}
	
	public Enemy summonBoss() //summon a boss monster.
	{
		Random rand = new Random();
		Connection con = SQLConnection.getConnection();
		ArrayList<Enemy> bosses = new ArrayList<Enemy>(5);

        try
        	{
        	con.setAutoCommit(false);
       	 	Statement stmt = con.createStatement();
       	 	String sql;
       	 	ResultSet rs = stmt.executeQuery(sql = "SELECT * FROM Enemy WHERE isBoss = 1");
       	 	System.out.println(sql);
            while (rs.next()) 
        	{
            bosses.add(new Enemy(
            		rs.getInt("EnemyID"), 
            		rs.getInt("MaxHP"), 
            		rs.getInt("isBoss"), 
            		rs.getInt("Attack"), 
            		rs.getInt("Defense"), 
            		rs.getInt("Speed"), 
            		rs.getInt("Intelligence"), 
            		rs.getInt("EXP"), 
            		rs.getInt("Money"), 
            		rs.getString("Name")));
        	}
			rs.close();
			stmt.close();
			con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        foe = bosses.get(rand.nextInt(bosses.size()));
        return foe;
	}
	
	public Damage calcPlrDamage(Character plr) //calculates the amount of damage the player will do to the current enemy
	{
		Damage damage = new Damage();
		int bonusAtk = 0;
		int bonusSpd = 0;
		if(plrDefended) {
			bonusSpd = plr.speed/2 + 2;
			damage.flags |= Damage.DMG_DEFBONUS;
			} 
		Random rand = new Random();
		//check to see if player will hit
		float speed = foe.speed - (plr.speed + bonusSpd);
		if(speed < 0) speed = 0;
		else if(speed > 15) speed = 15; //let's not make dodging too OP.
		speed *= 0.04;
		if(rand.nextFloat() >= speed)
		{
			if(plrDefended) bonusAtk = plr.getTotalAtk()/2 + 2;
			damage.damage = plr.getTotalAtk() + bonusAtk + rand.nextInt(5) - 2;
			
			float critchance = plr.intelligence;
			critchance *= 0.02;
			
			if(rand.nextFloat() <= critchance)
			{
				damage.flags |= Damage.DMG_CRIT;
				damage.damage *= 2;
			}
			
			damage.damage -= foe.defense;
			if(damage.damage <= 0) damage.damage = 1;
			plrDefended = false;
			return damage;
		}
		else
		{
			plrDefended = false;
			damage.flags |= Damage.DMG_DODGE;
			return damage;
		}
	}
	
	public boolean plrRun(Character plr) //calculate run chance, return true if successful
	{
		Random rand = new Random();
		int runBonus = 0;
		if(plrDefended) runBonus = plr.speed/2;
		int plrRun = plr.speed + runBonus + rand.nextInt(11);
		int eneCat = foe.speed + rand.nextInt(11);
		plrEscaped = plrRun > eneCat;
		return plrEscaped;
	}
	
	public Damage calcEnemyDamage(Character plr) //calculates the amount of damage the enemy will do to the player
	{
		Damage damage = new Damage();
		int bonusSpd = 0;
		int bonusDef = 0;
		if(plrDefended) {
			bonusSpd = plr.speed/2 + 3;
			damage.flags |= Damage.DMG_DEFBONUS;
			} 
		Random rand = new Random();
		//check to see if player will hit
		float speed = (plr.speed + bonusSpd) - foe.speed;
		if(speed < 0) speed = 0;
		else if(speed > 15) speed = 15; //let's not make dodging too OP.
		speed *= 0.04;
		if(rand.nextFloat() >= speed)
		{
			if(plrDefended) bonusDef = plr.getTotalDef()/2 + 5;
			damage.damage = foe.attack + rand.nextInt(5) - 2;
			
			float critchance = foe.intelligence;
			critchance *= 0.02;
			
			if(rand.nextFloat() <= critchance)
			{
				damage.flags |= Damage.DMG_CRIT;
				damage.damage *= 2;
			}
			
			damage.damage -= (plr.getTotalDef() + bonusDef);
			if(damage.damage <= 0) damage.damage = 1;
			return damage;
		}
		else
		{
			damage.flags |= Damage.DMG_DODGE;
			return damage;
		}
	}
	
	
	
	
}
