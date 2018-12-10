import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EmployeeManagement extends MainMenu implements SQLConstants {
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

    public void run(Scanner in, QueryProcessor qp)
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
                hire(in, qp);
            }
            else if (fCurrentSelection == 2) {
                System.out.println("Firing:");
                fire(in, qp);
            }
            else if (fCurrentSelection == 3) {
                System.out.println("Updating salary:");
                changeSalary(in, qp);
            }
            else if (fCurrentSelection == 4) {
                System.out.println("Updating personal information:");
                updateEmployeeInfo(in, qp);
            }

            if (fCurrentSelection > 4 || fCurrentSelection == 0)
                System.out.println("Your input was incorrect! Please try again.");

        } while(fCurrentSelection != -1);
    }

    private void hire(Scanner in, QueryProcessor qp)
    {
        System.out.println("Enter the new employee information");
        System.out.println("Fields marked with * are mandatory.");

        String FirstName = "";
        String LastName = "";
        String E_ID = "";       // this should be generated automatically
        String Manager_ID = ""; // this should be generated automatically
        String Address = "";
        String E_Position = "";
        Double Salary = 0.0;    // could be automatically set to the lowest value
        // of the same position
        int Dep_ID = 0;         // needs to be checked for existing values in DEPARTMENT

        // check all for NULLS?
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

        try {
            System.out.println("Salary:");
            Salary = Double.parseDouble(in.nextLine());
        } catch (NumberFormatException e) {
            fCurrentSelection = 0;
        }

        System.out.println("Department ID:");
        Dep_ID = in.nextInt();

        System.out.println("New EMPLOYEE: " + FirstName + " " +
                LastName + " " + E_ID + " " + Manager_ID + " " +
                Address + " " + E_Position + " " + Salary);

        String sqlString = INSERT + " " + INTO + " EMPLOYEE " +
                VALUES +"( \'" + FirstName + "\', \'" + LastName + "\', \'" +
                E_ID + "\', \'" + Manager_ID + "\', \'" + Address + "\', \'" +
                E_Position + "\', " + Salary + ", " +  Dep_ID + ")";

        qp.processQuery(sqlString);

    }

    private void fire(Scanner in, QueryProcessor qp)
    {
        // if I don't know E_ID, I could query for ID of FirstName and LastName
        // DO NOT delete by First and Last name - possible duplicate values
        System.out.println("To terminate an employee contract,");
        System.out.println("enter the employee's ID");

        String E_ID = in.nextLine();
        String sqlString = DELETE + " " + FROM  + " EMPLOYEE " +
                    WHERE + " E_ID = " + E_ID;
        qp.processQuery(sqlString);
    }

    private void changeSalary(Scanner in, QueryProcessor qp)
    {
        // if I don't know E_ID, I could query for ID of FirstName and LastName
        // DO NOT delete by First and Last name - possible duplicate values
        // TODO: raise salary of all employees in a department/position?
        System.out.println("To update an employee salary,");
        System.out.println("enter the employee's ID");

        String E_ID = in.nextLine();
        Double salary = in.nextDouble();

        String sqlString = "UPDATE EMPLOYEE SET Salary = " + salary +
                    " WHERE E_ID = " + E_ID;
        qp.processQuery(sqlString);
    }

    private void updateEmployeeInfo(Scanner in, QueryProcessor conn)
    {

    }

}
