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

        try
        	{
        	con.setAutoCommit(false);
       	 	Statement stmt = con.createStatement();
       	 	String sql;
       	 	ResultSet rs  = stmt.executeQuery(sql = "SELECT COUNT(townID) AS Test FROM Town WHERE townID = " + townID + ";");
       	 	System.out.println(sql);
                if(rs.getInt("Test") <= 0)
                {
                	name = "NOTOWN";
                	ID = -1;
                }
                else
                {
                rs = stmt.executeQuery(sql = "SELECT * FROM Town WHERE TownID = " + Integer.toString(townID));
                System.out.println(sql);
                name = rs.getString("TownName");
                ID = rs.getInt("TownID");
                items = new ArrayList<Item>(10);
                rs = stmt.executeQuery(sql ="SELECT Item.* FROM Item JOIN ItemInShop ON Item.ItemID = ItemInShop.ItemID WHERE TownID = " + Integer.toString(ID));
                System.out.println(sql);
                // loop through the result set
                while (rs.next()) 
                	{
                    items.add(new Item(
                    		rs.getInt("ItemID"),
                    		rs.getString("ItemName"),
                    		rs.getString("ItemDesc"),
                    		rs.getInt("Value"),
                    		rs.getInt("ItemType"),
                    		rs.getInt("Price"))
                    		);
                	}
                }
			rs.close();
			stmt.close();
			con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
	}
	
	public boolean exists()
	{
		if(ID >= 0) return true;
		else return false;
	}
	
	public int getItemID(int index)
	{
		return items.get(index).ID;
	}
	
	public String[] itemNameList() //for placement in a cbolist
	{
		String[] names = new String[items.size()]; 
		
		for(int i = 0; i < items.size(); ++i)
		{
			names[i] = items.get(i).name;
		}
		
		return names;
	}
	
	public String toString()
	{
		return name;
	}
}
