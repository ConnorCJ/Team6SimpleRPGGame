package simpleRPGgame;
import java.sql.*;

public class SQLConnection {
		
	public static Connection getConnection(){
		
		try{
		
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:GameDB.db");			
			return con;
				
		}catch(Exception e){
			System.out.println(e);
		}
			
		return null;
	}

}