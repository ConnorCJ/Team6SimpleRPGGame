package simpleRPGgame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Event {
	
	public int ID, stat, type, value, threshold, townID;
	public String desc, sucdesc, faildesc;
	
	public Event(int eventID) //get event based off of given ID
	{
		Connection con = SQLConnection.getConnection();
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event WHERE eventID = " + eventID + ";");
			
			ID = rs.getInt("EventID");
			stat = rs.getInt("EventStat");
			type = rs.getInt("EventType");
			value = rs.getInt("EventValue");
			threshold = rs.getInt("EventThreshold");
			townID = rs.getInt("EventTownID");
			desc = rs.getString("EventDesc");
			sucdesc = rs.getString("EventSucDesc");
			faildesc = rs.getString("EventFailDesc");
			
			rs.close();
			stmt.close();
			con.close();
			
		}catch (SQLException ex){
			System.err.println(ex);
			ex.printStackTrace();
		}
	}
	
	public Event() //an uneventful event. Get it? Har har.
	{
		ID = -1;
		stat = 0;
		type = 0;
		value = 0;
		threshold = 0;
		townID = 0;
		desc = "&n travels along, the next kilometer being uneventful.";
		sucdesc = "";
		faildesc = "";
	}
	
	public Event(int ID, int stat, int type, int value, int threshold, int townID, String desc, String sucdesc, String faildesc) //create event
	{
		this.ID = ID;
		this.stat = stat;
		this.type = type;
		this.value = value;
		this.threshold = threshold;
		this.townID = townID;
		this.desc = desc;
		this.sucdesc = sucdesc;
		this.faildesc = faildesc;
	}
	
	public boolean winEvent(Character chr)
	{
		if(type == 0 | type == 1) return true; //events of nothing or monsters auto-win.
		if(stat == 0) return true; //things where you do nothing auto-win.
		if(stat == 1)
			return chr.attack >= threshold;
		if(stat == 2)
			return chr.defense >= threshold;
		if(stat == 3)
			return chr.speed >= threshold;
		if(stat == 4)
			return chr.intelligence >= threshold;
		return true;
	}
	
	public String toString()
	{
		return "Event type: " + type;
	}
}
