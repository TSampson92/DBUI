import java.sql.Connection;
import java.util.Scanner;

public class MainMenu {
	private int fCurrentSelection;
	private boolean fDebugMenu;

	private void outputMenu() {
		System.out.println("****** Hallows Hotel Management System ******");
		System.out.println("\t1. Guest Management");
		System.out.println("\t2. Employee Management");
		System.out.println("\t3. Hotel Management");
		if (fDebugMenu)
			System.out.println("\t4.!SECRET!:DEBUG:IFYOUSEETHISSOMETHINGISWRONGUNLESSYOURPARTOFTHEDEVTEAMTHENPROCEED");
		
		System.out.println("Please make a selection. Enter -1 to exit:");
	}

	public MainMenu() {
		fCurrentSelection = 0;
		fDebugMenu = false;
	}

	public void run(QueryProcessor qp) {
		HotelManagement hManage = new HotelManagement();
		EmployeeManagement eManage = new EmployeeManagement();
		GuestManagement gManage = new GuestManagement();

		do {
			outputMenu();

			Scanner in = new Scanner(System.in);
			try {
				fCurrentSelection = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				fCurrentSelection = 0;
			}

			if (fCurrentSelection == 1)
				gManage.run();
			else if (fCurrentSelection == 2)
				eManage.run(in,qp);
			else if (fCurrentSelection == 3)
				hManage.run(in,qp);
			else if(fDebugMenu && fCurrentSelection == 4)
				doTheDebug(in,qp);
			else if (fCurrentSelection > 3 || fCurrentSelection == 0 || fCurrentSelection < -1)
				System.out.println("Your input was incorrect! Please try again.");

			//in.close();
		} while(fCurrentSelection != -1);


		System.out.println("Thank you for using the Hallows Hotel Management System!");


	}
	
	private void doTheDebug(Scanner in, QueryProcessor qp) {
		System.out.println("SUPER SPECIAL DEBUG AREA WHERE YOU CAN ENTER YOUR OWN SQL QUERIES YAY");
		System.out.println("Enter a sql query");
		String sqlState = in.nextLine();
		qp.processQuery(sqlState);
		
	}
	
	public void setDebug(boolean debug) {
		fDebugMenu = debug;
	}
	
}