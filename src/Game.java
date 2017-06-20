import java.sql.*;
import java.util.Scanner;

public class Game {
	
	public static void main(String[] args){
		
		// list characters as options for player?
		Scanner scan = new Scanner(System.in);
		
		int main_character = 1; // PlayerID
		String char_name;
		
		Connection con = SQLConnection.getConnection();
		
		try{
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CHARACTER;");
			
			while(rs.next()){
				System.out.println(rs.getString("Name"));
				System.out.println(rs.getInt("ID"));
				
				if(main_character == rs.getInt("ID")){
					char_name = rs.getString("Name");
					int HP = rs.getInt("MaxHP");
					System.out.println("You've selected:  "+ char_name);
					System.out.println("Your HP is:  " + HP);
					
				}
			}		
			
		}catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	
	public void toNextTown(){
		
	}
	public void getEvent(){
		
	}
	public void changeEnemy(){
		
	}
	public String getItem(){
		String item = "";
		
		return item;
	}
	
}
