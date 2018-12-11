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

    }

    private void displayAll(QueryProcessor qp){
        String sqlString = SELECT +  " LastName, FirstName " + FROM + " EMPLOYEE";
        qp.processQuery(sqlString);
    }

    private void displayByDepartment(Scanner in, QueryProcessor qp) {

    }

    private void displayByEmployee(Scanner in, QueryProcessor qp) {

    }

    private void displayByJobTitle(Scanner in, QueryProcessor qp) {
        
    }
}
