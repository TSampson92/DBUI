import java.util.Scanner;

public class DisplayEmployeeInfo extends EmployeeManagement implements SQLConstants {
    private int fCurrentSelection;

    public void DisplayInfo(){ fCurrentSelection = 0; }

    private void outputMenu(){
        System.out.println("\t1. Choose to display the information about General Manager"); // could add a short bio :D
        System.out.println("\t2. Choose to list all department managers");
        System.out.println("\t3. Choose to list all employees");
        System.out.println("\t4. Choose to display the list of employees in a particular department");
        System.out.println("\t5. Choose to display information about an individual employee");
        System.out.println("\t6. Choose to list all employees holding a particular job title");
        System.out.println("Please make a selection. Enter -1 to exit:");
    }

    public void run(Scanner in, QueryProcessor qp) {
        do {
            outputMenu();

            try {
                fCurrentSelection = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                fCurrentSelection = 0;
            }

            if(fCurrentSelection == 1)
                displayCeo(qp);
            else if(fCurrentSelection == 2)
                displayManagers(qp);
            else if(fCurrentSelection == 3)
                displayAll(qp);
            else if(fCurrentSelection == 4)
                displayByDepartment(in,qp);
            else if(fCurrentSelection == 5)
                displayByEmployee(in,qp);
            else if(fCurrentSelection == 6)
                displayByJobTitle(in,qp);

            else if (fCurrentSelection > 6 || fCurrentSelection == 0 || fCurrentSelection < -1)
                System.out.println("Your input was incorrect! Please try again.");

        } while (fCurrentSelection != -1);
    }

    private void displayCeo(QueryProcessor qp) {
        System.out.println("The general manager:");
        String sqlString = SELECT + " LastName, FirstName " + FROM + " EMPLOYEE " +
                WHERE + " Manager_ID " + IS_NULL;
        qp.processQuery(sqlString);
    }

    private void displayManagers(QueryProcessor qp){
        System.out.println("The managers of the hotel departments:");
        String sqlString = SELECT + " LastName, FirstName, Dep_Name " + FROM + " " +
                EMPLOYEE + " " + JOIN + " " + DEPARTMENT + " " + ON +
                " Dep_Head = E_ID";
        qp.processQuery(sqlString);
    }

    private void displayAll(QueryProcessor qp){
        String sqlString = SELECT +  " LastName, FirstName " + FROM + " EMPLOYEE";
        qp.processQuery(sqlString);
    }

    private void displayByDepartment(Scanner in, QueryProcessor qp) {
        // TODO: should be able to list by EITHER dept name OR dept number
        System.out.println("List employees in department:");

        System.out.println("Enter a department name:");
        String Dep_Name = in.nextLine();

        String sqlString = SELECT + " LastName, FirstName " + FROM + " " + EMPLOYEE +
                " AS E " + JOIN + " " + DEPARTMENT + " AS D " + ON + " E.Dep_ID = D.Dep_ID" +
                " " + WHERE + " D.Dep_Name = \'" + Dep_Name + "\'";
        qp.processQuery(sqlString);
    }

    private void displayByEmployee(Scanner in, QueryProcessor qp) {
        System.out.println("\t1.  Search for an employee by E_ID");
        System.out.println("\t2.  Search for an employee by first and last name");

        int choice = in.nextInt();
        in.nextLine();          // to avoid scanner skipping input after in.nextInt()
        if(choice == 1){
            System.out.println("\t\tEnter employee ID:");
            String id = in.nextLine();
            String sqlString = SELECT + " * " + FROM + " " + EMPLOYEE + " " +
                    WHERE + " E_ID = \'" + id + "\'";
            qp.processQuery(sqlString);
            System.out.println();

        } else if (choice == 2) {
            System.out.println("\t\tEnter employee first name:");
            String first = in.nextLine();
            System.out.println("\t\tEnter employee last name:");
            String last = in.nextLine();
            String sqlString = SELECT + " * " + FROM + " " + EMPLOYEE + " " +
                    WHERE + " FirstName = \'" + first + "\' AND LastName = \'" + last + "\'";
            qp.processQuery(sqlString);
            System.out.println();

        } else {
            System.out.println("Your input was incorrect! Please try again.");
        }
    }

    private void displayByJobTitle(Scanner in, QueryProcessor qp) {
        // display all with a position
        System.out.println("Enter job title:");
        System.out.println("Warning: Capitalize the first letter of the input.");
        String job = in.nextLine();
        String sqlString = SELECT + " LastName, FirstName " + FROM + " " + EMPLOYEE + " " +
                WHERE + " E_Position = \'" + job + "\'";
        qp.processQuery(sqlString);
        System.out.println();
    }
}
