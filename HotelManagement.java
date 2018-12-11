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
			if (depName.equals("")) {
				System.out.println("A department name is needed. Returning to Hotel Management menu.");
				return;
			}
			System.out.print("Enter a department ID. ID must be a positive integer value: ");
			try {
				depID = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.print("Department ID must be a positive integer value returning to Hotel Management menu. ");
				return;
			}
			
			System.out.print("Enter a manager ID that corresponds to a current employee ID: ");
			depHead = in.nextLine();
			if (depHead.equals("")) {
				System.out.println("A manager ID must be entered. Returning to Hotel Management menu.");
				return;
				
			}
			
			String sql = INSERT + " " +  INTO + " " + DEPARTMENT + " " + VALUES + "('" + depName + "', " + depID + ", " + 1 + ", '" + depHead + "');";
			qp.processQuery(sql);
			
			return;
		}
		
		private void assignDepartmentToRoom(Scanner in, QueryProcessor qp) {
			int department = 0;
			int room = 0;
			int choice = 0;
			System.out.println("Select a type of room to assign a department to:");
			System.out.println("\t1. Guest Room");
			System.out.println("\t2. Conference Room");
			try {
				choice = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("The choice has to be either 1 or 2. Returning to Hotel Management menu.");
				return;
			}
			if (choice != 1 && choice != 2) {
				System.out.println("Choice has to be either 1 or 2. Returning to Hotel management menu.");
				return;
			}
				
			System.out.print("Please enter a room number. This will fail if the room does not exists: ");
			try {
				room = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Room number must be a positive integer value. Returning to Hotel Management menu.");
				return;
			}
			
			System.out.print("Please enter a department number. This will fail if the department number does not exist: ");
			try {
				department = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Room number must be a positive integer value. Returning to Hotel Management menu.");
				return;
			}
			
			if (choice == 1) {
				String sql = INSERT + " " + INTO + " " + DEPARTMENT_SERVICES_GUEST + " " + VALUES + "(" + department + ", " + room + ");";
				qp.processQuery(sql);
			} else {
				String sql = INSERT + " " + INTO + " " + DEPARTMENT_SERVICES_CONFERENCE + " " + VALUES + "(" + department + ", " + room + ");";
				qp.processQuery(sql);
			}
				
		}
			
			
		private void printDepartmentInfo(Scanner in, QueryProcessor qp) {
			System.out.print("Enter a department to get the info from: ");
			String department = in.nextLine();
			String sql = SELECT + " * " + FROM + " " + DEPARTMENT + " " + WHERE + " " + DEPARTMENT +".Dep_Name='" + department + "';"; 
			qp.processQuery(sql);
			return;
		}
		
		private void printEmployeesInDepartment(Scanner in, QueryProcessor qp) {
			System.out.print("Enter a department to get the employees from: ");
			String department = in.nextLine();
			String sql = SELECT + " e.LastName, e.FirstName, d.Dep_Name " + FROM + " " + EMPLOYEE + " e " + JOIN + " " + DEPARTMENT + " d " 
															 + ON + " d.DEP_ID=e.Dep_ID " + WHERE + " d.Dep_name='" + department + "';" ;
			qp.processQuery(sql);
			return;
		}
		
		private void printRoomsServicedByDepartment(Scanner in, QueryProcessor qp) {
			int choice = 0;
			System.out.println("Select a type of room to search:");
			System.out.println("\t1. Guest Room");
			System.out.println("\t2. Conference Room");
			try {
				choice = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("The choice has to be either 1 or 2. Returning to Hotel Management menu.");
				return;
			}
			System.out.print("Enter a department to find the rooms it services: ");
			String department = in.nextLine();
			
			if (choice == 1) {
				String sql = SELECT + " DSGR.G_Room_Num, D.Dep_name " + FROM + " " + DEPARTMENT_SERVICES_GUEST + " DSGR " + JOIN + " " + DEPARTMENT
																	 + " D " + ON + " DSGR.Dep_ID=D.Dep_ID " + WHERE + " D.Dep_name='" + department + "';";
				qp.processQuery(sql);
			} else {
				String sql = SELECT + " DSCR.C_Room_Num, D.Dep_name " + FROM + " " + DEPARTMENT_SERVICES_CONFERENCE + " DSCR " + JOIN + " " + DEPARTMENT
						 + " D " + ON + " DSCR.Dep_ID=D.Dep_ID " + WHERE + " D.Dep_name='" + department + "';";
				qp.processQuery(sql);
			}
			
			return;
		}
		
		
		
		
}
