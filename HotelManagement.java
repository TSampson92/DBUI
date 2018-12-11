import java.util.Scanner;

public class HotelManagement extends MainMenu implements SQLConstants {
		private int fCurrentSelection;
		
		public void outputMenu( ) {
			System.out.println("HOTEL MANAGEMENT MENU");
			System.out.println("\t1. Create Department");
			System.out.println("\t2. Assign a Department to a Room");
			System.out.println("\t3. Print out Department Information");
			System.out.println("\t4. Print out all employees in a Department");
			System.out.println("\t5. Print out all Rooms that are serviced by a Department");
			System.out.println("Please make a selection. Enter -1 to return to the Main Menu:");
		}
	
		public void run(Scanner in, QueryProcessor qp) {
			
			do {
				outputMenu();
				try {
					fCurrentSelection = Integer.parseInt(in.nextLine());
				} catch (NumberFormatException e) {
					fCurrentSelection = 0;
				}
				switch (fCurrentSelection) {
					case 1:
						createDepartment(in,qp);
						break;
					case 2:
						assignDepartmentToRoom(in,qp);
						break;
					case 3:
						printDepartmentInfo(in,qp);
						break;
					case 4:
						printEmployeesInDepartment(in,qp);
						break;
					case 5:
						printRoomsServicedByDepartment(in,qp);
						break;
					case -1:
						break;
					default:
						System.out.println("Your input was incorrect! Please try again.");
				}
				
			} while (fCurrentSelection != -1);
			
			return;
		}
		
		
		private void createDepartment(Scanner in, QueryProcessor qp) {
			System.out.println("Enter the information needed to create a new department:");
			System.out.println("All fields are mandatory to create a new Department");
			String depName;
			int depID;
			String depHead;
			
			System.out.print("Enter the name of the Department: ");
			depName = in.nextLine();
			System.out.print("Enter a department ID: ");
			try {
				depID = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.print("Department ID must be a positive integer value: ");
				depID = Integer.parseInt(in.nextLine());
			}
			
			System.out.print("Enter a manager ID that corresponds to a current employee ID: ");
			depHead = in.nextLine();
			
			String sql = INSERT + " " +  INTO + " " + DEPARTMENT + " " + VALUES + "('" + depName + "', " + depID + ", " + 1 + ", '" + depHead + "')";
			qp.processQuery(sql);
			
			return;
		}
		
		private void assignDepartmentToRoom(Scanner in, QueryProcessor qp) {
			return;
		}
		
		private void printDepartmentInfo(Scanner in, QueryProcessor qp) {
			return;
		}
		
		private void printEmployeesInDepartment(Scanner in, QueryProcessor qp) {
			return;
		}
		
		private void printRoomsServicedByDepartment(Scanner in, QueryProcessor qp) {
			return;
		}
		
		
		
		
}
