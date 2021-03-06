package simpleRPGgame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Item {
	public String name, desc;
	public int ID, value, price, type;
	private final int MAXTYPES = 4;
	
	public Item(int itemID) //get an item based off of the given item ID
	{
		Connection con = SQLConnection.getConnection();

        try
        	{
        	con.setAutoCommit(false);
       	 	Statement stmt = con.createStatement();
       	 	String sql;
    	 	ResultSet rs  	= stmt.executeQuery(sql = "SELECT COUNT(itemID) AS Test FROM Item WHERE itemID = " + itemID + ";");
    	 	System.out.println(sql);
       	 	if(rs.getInt("Test") > 0)
    	 	{
       	 		rs    = stmt.executeQuery(sql = "SELECT * FROM Item WHERE ItemID = " + Integer.toString(itemID));
       	 		System.out.println(sql);
                name = rs.getString("ItemName");
                desc = rs.getString("ItemDesc");
                ID = rs.getInt("ItemID");
                value = rs.getInt("Value");
                price = rs.getInt("Price");
                type = rs.getInt("ItemType");
                if(type > MAXTYPES) type = 0; //invalid type
            }
       	 	else
       	 	{
       	 		name = "Invalid Item";
       	 		desc = "A missing item tried to get accessed? Free 10 HP.";
       	 		ID = 0; //it'll be transformed into a potion, I guess?
       	 		value = 10;
       	 		price = 1;
       	 		type = 0;
       	 	}
       	 	
	       	rs.close();
	 		stmt.close();
	 		con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public Item(int itemID, String itemName, String itemDesc, int itemValue, int itemType, int itemPrice) //produce item based off of information
	{
		ID = itemID;
		name = itemName;
		desc = itemDesc;
		value = itemValue;
		type = itemType;
		price = itemPrice;
	}
	
	public String getTypeName()
	{
		String itemTypes[] = {"Healing","Weapon","Armor","EXP Gain"};
		return itemTypes[type];
	}
	
	public String toString()
	{
		return name;
	}
	

	
	
}
