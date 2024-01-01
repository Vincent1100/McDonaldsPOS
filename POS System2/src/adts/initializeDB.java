package adts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class initializeDB {
	protected Statement statement;
	
	public initializeDB()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "VinPet1!");
			statement = connection.createStatement();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
