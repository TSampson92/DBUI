import java.sql.*;

public class DBmain {
	public static void main (String[] args) throws SQLException {
			QueryProcessor sqlProcessor = new QueryProcessor();
			try {
				sqlProcessor.initializeConnection();
			} catch(Exception e) {
				System.out.println("Error attempting to open connection to the Database");
				System.exit(0);
			}

			//String sqlStatement = in.nextLine();
			MainMenu mainMenu = new MainMenu();
			mainMenu.setDebug(true);
			mainMenu.run(sqlProcessor);
            
	}
}