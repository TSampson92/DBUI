import java.io.FileReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.sql.*;

public class QueryProcessor {
	
	private Connection fSQLConnection;
	private String[] fConnectionParams;

	public QueryProcessor() {
		fSQLConnection = null;
	}
	
	public void initializeConnection() throws SQLException, Exception {
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		fConnectionParams = getConnectionParams();
		fSQLConnection = DriverManager.getConnection(fConnectionParams[0],fConnectionParams[1],fConnectionParams[3]);
		//With connection set up, test by Setting the correct DB: HOTEL
		Statement DBSelectStmt = fSQLConnection.createStatement();
		DBSelectStmt.execute("USE HOTEL");
		//Connection should be open and ready for statements sent from the menus
	}
	
	
	
	private String[] getConnectionParams() throws FileNotFoundException {
		String[] paramArray = new String[3];
		FileReader paramInput = null;
		try {
			paramInput = new FileReader("DBConnectConfig.txt");
		} catch (FileNotFoundException e) {
			System.out.println("The configuration file cannot be found. Exiting Program.");
			e.printStackTrace();
			System.exit(0);
		}
		
		Scanner inFile = new Scanner(paramInput);
		int i = 0;
		while (inFile.hasNext()) {
			String input = inFile.nextLine();
			String[] tokens = input.split("=");
			paramArray[i] = tokens[1];
			i++;
		}
		
		inFile.close();
		return paramArray;
	}
	

}
