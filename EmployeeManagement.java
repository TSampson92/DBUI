import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EmployeeManagement extends MainMenu implements SQLConstants {
    private int fCurrentSelection;

    private void outputMenu()
    {
        System.out.println("EMPLOYEE INFORMATION MENU");
        System.out.println("\t1. Hire a new employee");
        System.out.println("\t2. Terminate an employee contract");
        System.out.println("\t3. Manage employee salary");
        System.out.println("\t4. Update an employee information");
        System.out.println("\t5. Promote an employee to a manager of a department");
        System.out.println("\t6. Display employee information");
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
                System.out.println("HIRING:");
                hire(in, qp);
            }
            else if (fCurrentSelection == 2) {
                System.out.println("FIRING:");
                fire(in, qp);
            }
            else if (fCurrentSelection == 3) {
                System.out.println("UPDATING SALARY:");
                changeSalary(in, qp);
            }
            else if (fCurrentSelection == 4) {
                System.out.println("UPDATING EMPLOYEE INFORMATION:");
                updateEmployeeInfo(in, qp);
            }
            else if (fCurrentSelection == 5) {
                System.out.println("DISPLAY EMPLOYEE INFORMATION");
                promoteEmployee(in, qp);
            }
            else if (fCurrentSelection == 6) {
                System.out.println("DISPLAY EMPLOYEE INFORMATION");
                displayInfo(in, qp);
            }

            if (fCurrentSelection > 5 || fCurrentSelection == 0)
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
        System.out.println("Warning: The system will report error if now fields are changed.");
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

        System.out.println("Department ID:");
        int Dep_ID = 0;
        try {
            Dep_ID = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException | NoSuchElementException e) {
            // no value was entered, department exists, value doesn't change
            Dep_ID = -1;
            deptExists = true; }

        String sqlString = UPDATE + " EMPLOYEE " + SET;
        if(!FirstName.equals("") && !FirstName.equals("NULL")) {
            sqlString += " FirstName = \'" + FirstName + "\'";
            firstNameChanged = true;
        }
        if(!LastName.equals("") && !LastName.equals("NULL")) {
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
        if(!E_Position.equals("") && !E_Position.equals("NULL")) {
            if(firstNameChanged || lastNameChanged || addressChanged) {
                sqlString += ",";
            }
            sqlString += " E_Position = \'" + E_Position + "\'";
            positionChanged = true;
        }
        if(Dep_ID != -1) {
            // check that department with the requested ID exists
            Statement stmt = null;
            String deptExistsQuery = SELECT + " Dep_ID, Dep_Head " + FROM + " DEPARTMENT";
            String depHead = "";

            try{
                ResultSet rs = qp.processForResultSet(deptExistsQuery);

                // search through result set
                while(rs.next()) {
                    int depID = rs.getInt("Dep_ID");
                    depHead = rs.getString("Dep_Head");
                    if(depID == Dep_ID) {
                        deptExists = true;
                        break;
                    }
                }
                if(!deptExists) {
                    // illegal attempt to change dept number to a non-existing department
                    System.out.println("Department doesn't exist.");
                    System.out.println("Operation terminated");
                    return;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            if(firstNameChanged || lastNameChanged || addressChanged || positionChanged) {
                sqlString += ",";
            }
            sqlString += " Dep_ID = " + Dep_ID + ", ";
            sqlString += " Manager_ID = \'" + depHead + "\'";
        }

        if(Dep_ID == -1) {
            if (!firstNameChanged && !lastNameChanged && !addressChanged && !positionChanged) {
                System.out.println("No values were changed.");
                return;
            }
        }

        if(!deptExists) {
            // illegal attempt to change dept number to a non-existing department
            System.out.println("Department doesn't exist.");
            System.out.println("Operation terminated\n");
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
        System.out.println("Employee information updated \n");
    }

    private void promoteEmployee(Scanner in, QueryProcessor qp){
        System.out.println("Enter ID of the employee to promote:");
        String E_ID = in.nextLine();

        System.out.println("Position:");
        String E_Position = in.nextLine();

        boolean deptExists = false;
        System.out.println("Department ID:");
        int Dep_ID = 0;
        try {
            Dep_ID = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException | NoSuchElementException e) {
            // no value was entered, department exists, value doesn't change
            Dep_ID = -1;
            deptExists = true; }

        if(Dep_ID != -1) {
            // check that department with the requested ID exists
            Statement stmt = null;
            String deptExistsQuery = SELECT + " Dep_ID " + FROM + " DEPARTMENT";
            String depHead = "";

            try{
                ResultSet rs = qp.processForResultSet(deptExistsQuery);

                // search through result set
                while(rs.next()) {
                    int depID = rs.getInt("Dep_ID");
                    if(depID == Dep_ID) {
                        deptExists = true;
                        break;
                    }
                }
                if(!deptExists) {
                    // illegal attempt to change dept number to a non-existing department
                    System.out.println("Department doesn't exist.");
                    System.out.println("Operation terminated");
                    return;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        if(!deptExists) {
            // illegal attempt to change dept number to a non-existing department
            System.out.println("Department doesn't exist.");
            System.out.println("Operation terminated\n");
            return;
        }

        String sqlString = UPDATE + " EMPLOYEE " + SET + " Position = \'" +  E_Position +
                "\', Manager_ID = \'00000\' " + WHERE + " E_ID = " + E_ID;

        System.out.println("Employee number " + E_ID + " promoted to a manager. Congratulations!\n");

    }

    private void displayInfo(Scanner in, QueryProcessor qp)
    {
        DisplayEmployeeInfo display = new DisplayEmployeeInfo();
        display.run(in, qp);
    }
}
