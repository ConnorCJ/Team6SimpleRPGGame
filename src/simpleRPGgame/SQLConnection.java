package simpleRPGgame;
import java.sql.*;
import javax.swing.*;

public class SQLConnection {
		
	public static Connection getConnection(){
			
		try{
		
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:GameDB.db");			
			//System.out.println("Connected to \n" + con);
			return con;
				
		}catch(Exception e){
			System.out.println(e);
		}
			
		return null;
	}

}