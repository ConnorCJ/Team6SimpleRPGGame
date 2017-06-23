package simpleRPGgame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Town {
	public int ID;
	public String name;
	public ArrayList<Item> items;
	
	public Town(int townID)
	{
		Connection con = SQLConnection.getConnection();
		String sql = "SELECT * FROM Town WHERE TownID = " + Integer.toString(townID);

        try
        	{
        	con.setAutoCommit(false);
       	 	Statement stmt = con.createStatement();
       	 	ResultSet rs    = stmt.executeQuery(sql);
            
       	 	if(rs.absolute(1))
       	 	{
                name = rs.getString("TownName");
                ID = rs.getInt("TownID");
                items = getItemsInShop();
       	 	}
       	 	else //This is a means of detecting if there's no new town ahead - meaning the player is approaching The End, where the boss lies. Check to see if ID == -1.
       	 	{
       	 		name = "NONEXISTENTTOWN";
       	 		ID = -1;
       	 	}
                
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
	}
	
	public boolean exists()
	{
		if(ID >= 0) return true;
		else return false;
	}
	
	public ArrayList<Item> getItemsInShop()
	{
		String sql = "SELECT Item.* FROM Item JOIN ItemInShop ON Item.ItemID = ItemInShop.ItemID WHERE TownID = " + Integer.toString(ID);
		Connection con = SQLConnection.getConnection();
		ArrayList<Item> i = new ArrayList<Item>(20);
		
		try
    	{
    	con.setAutoCommit(false);
   	 	Statement stmt = con.createStatement();
   	 	ResultSet rs    = stmt.executeQuery(sql);
        
        // loop through the result set
        while (rs.next()) {
            i.add(new Item(
            		rs.getInt("ItemID"),
            		rs.getString("ItemName"),
            		rs.getString("ItemDesc"),
            		rs.getInt("Value"),
            		rs.getInt("ItemType"),
            		rs.getInt("Price"))
            		);
        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
		
		return i;
	}
}
