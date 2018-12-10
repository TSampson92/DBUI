import java.util.Scanner;

public class EmployeeManagement extends MainMenu {
    private int fCurrentSelection;

    private void outputMenu()
    {
        System.out.println("\t1. Hire a new employee");
        System.out.println("\t2. Terminate an employee contract");
        System.out.println("\t3. Manage employee salary");
        System.out.println("\t4. Update en employee information");
        System.out.println("Please make a selection. Enter -1 to return to the Main Menu:");
    }

    public EmployeeManagement()
    {
        fCurrentSelection = 0;
    }

    public void run(Scanner in)
    {

        do {
            outputMenu();

            try {
                fCurrentSelection = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                fCurrentSelection = 0;
            }

            if (fCurrentSelection == 1) {
                System.out.println("Hiring:");
                hire(in);
            }
            else if (fCurrentSelection == 2) {
                System.out.println("Firing:");
                fire(in);
            }
            else if (fCurrentSelection == 3) {
                System.out.println("Updating salary:");
                changeSalary(in);
            }
            else if (fCurrentSelection == 4) {
                System.out.println("Updating personal information:");
                updateEmployeeInfo(in);
            }

            if (fCurrentSelection > 4 || fCurrentSelection == 0)
                System.out.println("Your input was incorrect! Please try again.");

        } while(fCurrentSelection != -1);
    }

    private void hire(Scanner in)
    {
        System.out.println("Enter the new employee information");
        System.out.println("Fields marked with * are mandatory.");

        String FirstName = "";
        String LastName = "";
        String E_ID = "";       // this should be generated automatically
        String Manager_ID = ""; // this should be generad automatically
        String Address = "";
        String E_Position = "";
        Double Salary = 0.0;    // could be automatically set to the lowest value
                                // of the same position
        int Dep_ID = 0;         // needs to be checked for existing values in DEPARTMENT

        try {
            System.out.println("* First name:");
            FirstName = in.nextLine();

            System.out.println("* Last name:");
            LastName = in.nextLine();

            System.out.println("* Employee ID:");
            E_ID = in.nextLine();

            System.out.println("Manger ID:");
            Manager_ID = in.nextLine();

            System.out.println("Address:");
            Address = in.nextLine();

            System.out.println("* Position:");
            E_Position = in.nextLine();

            System.out.println("Salary:");
            Salary = Double.parseDouble(in.nextLine());

        } catch (NumberFormatException e) {
            fCurrentSelection = 0;
        }

        System.out.println("EMPLOYEE: " + FirstName + " " +
                LastName + " " + E_ID + " " + Manager_ID + " " +
                Address + " " + E_Position + " " + Salary);
    }

    private void fire(Scanner in)
    {

    }

    private void changeSalary(Scanner in)
    {

    }

    private void updateEmployeeInfo(Scanner in)
    {

    }

}