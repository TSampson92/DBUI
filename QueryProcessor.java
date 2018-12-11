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
	
	@SuppressWarnings("deprecation")
	public void initializeConnection() throws SQLException, Exception {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		fConnectionParams = getConnectionParams();
		fSQLConnection = DriverManager.getConnection(fConnectionParams[0],fConnectionParams[1],fConnectionParams[2]);
		//With connection set up, test by Setting the correct DB: HOTEL
		Statement DBSelectStmt = fSQLConnection.createStatement();
		DBSelectStmt.execute("USE HOTEL");
		//Connection should be open and ready for statements sent from the menus
	}
	
	public ResultSet processForResultSet(String sqlStatement) {
		ResultSet rs = null;
		try {
			PreparedStatement ps = fSQLConnection.prepareStatement(sqlStatement);
			boolean resultsReturned = ps.execute();
			if (resultsReturned)
				rs = ps.getResultSet();
			else {
				System.out.println("No results returned");
				return rs;
			}
		} catch (SQLException sqlE) {
			System.out.println("Error processing the sql statement: " + sqlE.getMessage());
			return rs;
		}
		 
		return rs;
	}
	
	public void processQuery(String sqlStatement) {
		try {
		PreparedStatement ps = fSQLConnection.prepareStatement(sqlStatement);
		boolean psExecuted = ps.execute();
		ResultSet rs = null;
		if (psExecuted)
			rs = ps.getResultSet();
		else {
			System.out.println("No results returned.");
			return;
		}
		
		ResultSetMetaData meta = rs.getMetaData();
		int numberOfColumns = meta.getColumnCount();
		//System.out.println("Number of Columns: " + numberOfColumns);
		for (int i = 1; i <= numberOfColumns; i++) {
			String columnType = meta.getColumnTypeName(i);
			//System.out.print(columnType + " ");
			if (columnType.equals("VARCHAR"))
				System.out.printf("|%-5s", meta.getColumnName(i));
			else
				System.out.printf("|%s",meta.getColumnName(i));
			
		}
		System.out.print('|');
		System.out.println();
		while (rs.next() != false) {
			for (int i = 1; i <= numberOfColumns; i++ ) {
				String columnType = meta.getColumnTypeName(i);
				if (columnType.equals("VARCHAR"))
					System.out.printf("|%-5s", rs.getString(i));
				else
					System.out.printf("|%5s",rs.getString(i));
			}
			System.out.print('|');
			System.out.println();
			}
		} catch (SQLException sqlE) {
			System.out.println("Error performing SQL Query: " + sqlE.getMessage());
		}
	}
	
	
	
	private String[] getConnectionParams() throws FileNotFoundException {
		String[] paramArray = new String[3];
		FileReader paramInput = null;
		try {
			paramInput = new FileReader("src/DBConnectConfig.txt");
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
