import java.sql.Connection;
import java.util.Scanner;

public class MainMenu {
	private int fCurrentSelection;

	private void outputMenu() {
		System.out.println("****** Hallows Hotel Management System ******");
		System.out.println("\t1. Guest Management");
		System.out.println("\t2. Employee Management");
		System.out.println("\t3. Hotel Management");
		System.out.println("Please make a selection. Enter -1 to exit:");
	}

	public MainMenu() {
		fCurrentSelection = 0;
	}

	public void run(Connection connDB) {
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
				System.out.println("Guest management");
				//gManage.run();
			else if (fCurrentSelection == 2)
				eManage.run(in);
			else if (fCurrentSelection == 3)
				System.out.println("Hotel management");
				//hManage.run();

			if (fCurrentSelection > 3 || fCurrentSelection == 0)
				System.out.println("Your input was incorrect! Please try again.");

			//in.close();
		} while(fCurrentSelection != -1);


		System.out.println("Thank you for using the Hollows Hotel Management System!");


	}
	
}