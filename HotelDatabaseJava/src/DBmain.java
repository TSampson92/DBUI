import java.sql.*;
import java.util.Scanner;

public class DBmain {
	@SuppressWarnings("deprecation")
	public static void main (String[] args) throws SQLException {
			//Scanner in = new Scanner(System.in);
			Connection conSQL = null;

		try {
			//String sqlStatement = in.nextLine();
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conSQL = DriverManager.getConnection("jdbc:mysql://vergil.u.washington.edu:5555/HOTEL", "petra", "petra1");
			Statement stmt=conSQL.createStatement();
			stmt.execute("USE HOTEL");
			/*
			PreparedStatement ps = conSQL.prepareStatement(sqlStatement);
			boolean psEx = ps.execute();
			ResultSet rs = null;
			if (psEx)
				rs = ps.getResultSet();

			ResultSetMetaData meta = rs.getMetaData();
			int numberOfColumns = meta.getColumnCount();
			System.out.println("Number of Columns: " + numberOfColumns);
			for (int i = 1; i <= numberOfColumns; i++) {
				System.out.print('|' + meta.getColumnName(i));
			}
			System.out.print('|');
			System.out.println();
			while (rs.next() != false) {
				for (int i = 1; i <= numberOfColumns; i++ ) {
					System.out.print('|' + rs.getString(meta.getColumnName(i)));
				}
				System.out.print('|');
				System.out.println();
			}
			*/
            //in.close();
			MainMenu mainMenu = new MainMenu();
			mainMenu.run(conSQL);
            
		} catch(Exception e) {
			System.out.println(e.getMessage());
			if(conSQL != null)
				conSQL.close();
		
		}
	}
}
