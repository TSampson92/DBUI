import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
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

        System.out.println("Salary:");
        Salary = in.nextDouble();

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

        System.out.println("enter the new salary");

        Double salary = in.nextDouble();

        String sqlString = UPDATE + " EMPLOYEE " + SET + " Salary = " + salary +
                    " " + WHERE + " E_ID = " + E_ID;
        qp.processQuery(sqlString);
    }

    private void updateEmployeeInfo(Scanner in, QueryProcessor qp)
    {
        System.out.println("Update employee personal information. If you want a field to stay the same, ");
        System.out.println("press Enter at the appropriate prompt.");
        System.out.println("To set a field to \'N\\A\' or \'no value\' status, type NULL");
        System.out.println("Warning: First name, Last name, and Position cannot be set to \'N\\A\' or \'no value\'.");
        System.out.println("Warning: Employee number cannot be changed.");

        boolean firstNameChanged = false;
        boolean lastNameChanged = false;
        boolean addressChanged = false;
        boolean positionChanged = false;
        boolean deptExists = false;

        System.out.println("Enter ID of the employee to update:");
        String E_ID = in.nextLine();

        System.out.println("Enter the new information:");
        System.out.println("First name:");
        String FirstName = in.nextLine();

        System.out.println("Last name:");
        String LastName = in.nextLine();

        System.out.println("Address:");
        String Address = in.nextLine();

        System.out.println("Position:");
        String E_Position = in.nextLine();

        // manager ID will change based on department
        System.out.println("Department ID:");
        int Dep_ID = 0;
        try {
            Dep_ID = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException | NoSuchElementException e) {
            // invalid, value, don't change the current value
            Dep_ID = -1;
            deptExists = true; }

        String sqlString = UPDATE + " EMPLOYEE " + SET;
        if(!FirstName.equals("")) {
            sqlString += " FirstName = \'" + FirstName + "\'";
            firstNameChanged = true;
        }
        if(!LastName.equals("")) {
            if(firstNameChanged){
                sqlString += ",";
            }
            sqlString += " LastName = \'" + LastName + "\'";
            lastNameChanged = true;
        }
        if(!Address.equals("")) {
            if(firstNameChanged || lastNameChanged) {
                sqlString += ",";
            }
            if(Address.equals("NULL")){
                sqlString += " Address = \'\'";
            } else {
                sqlString += " Address = \'" + Address + "\'";
            }
            addressChanged = true;
        }
        if(!E_Position.equals("")) {
            if(firstNameChanged || lastNameChanged || addressChanged) {
                sqlString += ",";
            }
            sqlString += " E_Position = \'" + E_Position + "\'";
            positionChanged = true;
        }
        if(Dep_ID != 0 || Dep_ID != -1) {
            // check that department exists
            // should have method in QP that returns ResultSet
            Statement stmt = null;
            String deptExistsQuery = SELECT + " Dep_ID " + FROM + " DEPARTMENT";

            try{
                stmt = qp.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(deptExistsQuery);

                // search through result set
                while(rs.next()) {
                    int depID = rs.getInt("Dep_ID");
                    if(depID == Dep_ID) {
                        deptExists = true;
                        break;
                    }
                }
                if(!deptExists) {
                    return;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            if(firstNameChanged || lastNameChanged || addressChanged || positionChanged) {
                sqlString += ",";
            }
            sqlString += " Dep_ID = " + Dep_ID;
        }

        if(!deptExists) {
            // illegal attempt to change dept number to a non-existing department
            System.out.println("Department doesn't exist.");
            System.out.println("Operation terminated");
            return;
        }

        sqlString += " " + WHERE + " E_ID = " + E_ID;

        if(FirstName == "NULL" || LastName == "NULL" || E_Position == "NULL")
        {
            System.out.println("First name, Last name, and Position cannot be set to \'N\\A\' or \'no value\'.");
            System.out.println("Operation terminated");
            return;
        }

        qp.processQuery(sqlString);
    }

}
