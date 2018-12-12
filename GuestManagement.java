import java.util.Scanner;
import java.sql.*;

public class GuestManagement extends MainMenu implements SQLConstants {
	private int fCurrentSelection;
	private static int fNumberOfGuestReservations;
	private static int fLastGuestReservationCleared;
	private static int fNumberOfConferenceReservations;
	private static int fLastConferenceReservationCleared;
	
	public GuestManagement() {
		fCurrentSelection = 0;
	}

	public void outputMenu() {
		System.out.println("GUEST MANAGEMENT MENU");
		System.out.println("\t1. Create a reservation for a new guest");
		System.out.println("\t2. List guest contact information");
		System.out.println("\t3. List all guests in alphabetical order");
		System.out.println("\t4. List all guests staying at the hotel on a particular date");
		System.out.println("\t5. List all guests arriving on a particular date");
		System.out.println("Please make a selection. Enter -1 to return to the Main Menu:");
	}
	
	public void run(Scanner in, QueryProcessor qp) {
		fLastGuestReservationCleared = 0;
		fLastConferenceReservationCleared = 0;
		fNumberOfGuestReservations = qp.getCountFromTable(GUEST_RESERVATION);
		fNumberOfConferenceReservations = qp.getCountFromTable(CONFERENCE_RESERVATION);
		
		do{
			outputMenu();

			try {
				fCurrentSelection = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				fCurrentSelection = 0;
			}

			if (fCurrentSelection == 1) {
				System.out.println("CREATING RESERVATION:");
				createReservation(in, qp);
			} else if (fCurrentSelection == 2) {
				System.out.println("LISTING GUEST CONTACT INFORMATION:");
				listContactInfo(in, qp);
			} else if (fCurrentSelection == 3) {
				System.out.println("LISTING ALL GUEST:");
				listAllGuests(qp);
			} else if (fCurrentSelection == 4) {
				System.out.println("LISTING ALL GUEST WITH RESERVATION FOR DATE:");
				listGuestsStayingOnDate(in, qp);
			} else if (fCurrentSelection == 5) {
				System.out.println("LIST ALL GUEST ARRIVING ON DATE:");
				listGuestsArrivingOnDate(in, qp);
			}

			if (fCurrentSelection > 5 || fCurrentSelection == 0 || fCurrentSelection < -1)
				System.out.println("Your input was incorrect! Please try again.");

		} while(fCurrentSelection != -1);
	}

	private void createReservation(Scanner in, QueryProcessor qp){
		boolean guestExists = false;
		String gEmail = "";
		String gName = "";
		
		System.out.print("Please enter the name of the Guest: " );
		gName = in.nextLine();
		System.out.print("Please enter the email for the Guest: ");
		gEmail = in.nextLine();
		
		guestExists = guestHasReservation(gName, gEmail, qp);
		if (guestExists)
			System.out.println("Welcome back " + gName);
		String startDate = "";
		String endDate = "";
		//When they want to be here
		System.out.println("Please enter the start date of the reservation. Please enter as DD-MM-YYYY: ");
		startDate = in.nextLine();
		System.out.println("Please enter the end date of the reservation. Please enter as DD-MM-YYYY: ");
		endDate = in.nextLine();
		int smokingStatus = 0;
		int valid = 0;
		while (valid == 0) {
			System.out.println("Choose whether the room is smoking or non smoking");
			System.out.println("\t1.Non Smoking");
			System.out.println("\t2.Smoking");
			
			try {
				valid = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input, please try again");
				valid = 0;
				continue;
			}
			
			if (valid != 1 && valid != 2) {
				System.out.println("Invalid choice, please try again.");
				valid = 0;
				continue;
			}
		}
		
		switch (valid) {
			case 1:  
				smokingStatus = 0;
				break;
			case 2:
				smokingStatus = 1;
				break;
		}
		String bedType = "";
		while (valid == 0) {
			System.out.println("Choose what bed type you want for room");
			System.out.println("\t1.Twin");
			System.out.println("\t2.Full");
			System.out.println("\t3.Queen");
			System.out.println("\t4.King");
			
			
			try {
				valid = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input, please try again");
				valid = 0;
				continue;
			}
			
			if (valid > 4 && valid < 1) {
				System.out.println("Invalid choice, please try again.");
				valid = 0;
				continue;
			}
		}
		switch (valid) {
		case 1:  
			bedType = "Twin";
			break;
		case 2:
			bedType = "Full";
			break;
		case 3:
			bedType = "Queen";
			break;
		case 4:
			bedType = "King";
			break;
		}
		
		startDate = formatDate(startDate);
		endDate = formatDate(endDate);
		String roomSQL = SELECT + " GR.Room_num " + FROM + " " + GUEST_ROOM + " AS GR " + LEFT_JOIN + " " + GUEST_RESERVATION + " AS GRSV " + ON + " GR.Room_num=GRSV.Room_Number " + WHERE 
																		+ " ((GRSV.START_DATE < "+ startDate + " AND GRSV.END_DATE <  " + startDate + ") OR (GRSV.START_DATE > " + startDate + " AND GRSV.END_DATE > " + startDate + "))" 
																		+ " AND GR.Bed_Type='" + bedType + "' AND GR.Smoking_Status=" + smokingStatus + ";";
		System.out.println("Rooms available matching these criteria:");
		qp.processQuery(roomSQL);
		
		int roomNumber = 0;
		while (roomNumber == 0) {
			System.out.print("Please select a room: ");
			try {
			  roomNumber = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid Choice, please select again.") ;
				roomNumber = 0;
				continue;
			}
			
			if (roomNumber <= 0 || roomNumber > 200) {
				System.out.println("Invalid room number. Please select again.");
				roomNumber = 0;
				continue;
			}
		}
		int numberOfGuests = 0;
		while (numberOfGuests == 0) {
			System.out.print("Please enter number of guests. A maximum of 5 guests can be in a room: ");
			try {
			  numberOfGuests = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid Choice, please select again."); 
				numberOfGuests = 0;
				continue;
			}
			
			if (numberOfGuests <= 0 || numberOfGuests > 5) {
				System.out.println("Invalid number of guests. Number of guests must be in a range of 1 to 5.");
				numberOfGuests = 0;
				continue;
			}
		}
		
		if (guestExists) {
			int reservationID;
			if (fLastGuestReservationCleared == 0) {
				fNumberOfGuestReservations++;
				reservationID = fNumberOfGuestReservations;
			} else {
				fNumberOfGuestReservations++;
				reservationID = fLastGuestReservationCleared;
				fLastGuestReservationCleared = 0;
			}
					
			String reservationInsert = INSERT + " " + INTO + GUEST_RESERVATION + " " + VALUES + " ('" + reservationID + "', '" + gName + "', " + roomNumber + ", " + numberOfGuests + ", '" + startDate + "', '" + endDate + "', '" + gEmail + "')";
			qp.processQuery(reservationInsert);
			System.out.println("Done creating reservation.");
		} else {
			int reservationID;
			if (fLastGuestReservationCleared == 0) {
				fNumberOfGuestReservations++;
				reservationID = fNumberOfGuestReservations;
			} else {
				fNumberOfGuestReservations++;
				reservationID = fLastGuestReservationCleared;
				fLastGuestReservationCleared = 0;
			}
			System.out.println("Since this is " + gName + "'s first time at the Hallows Hotel, please provide some information.");
			System.out.print("Please enter the guest's Address: ");
			String gAddress = in.nextLine();
			System.out.print("Please enter the guest's credit card number. [16 digits]: ");
			String gCCNum = in.nextLine();
			System.out.print("Please enter the guest's phone number including country code [Up to 13 digits]: ");
			String gPhoneNumber = in.nextLine();
			
			String guestInsert = INSERT + " " + INTO + " " + GUEST + " " + VALUES + " ('" + gEmail + "', '" + gName + "', '" + gCCNum + "', '" + gPhoneNumber + "', '" + gAddress + "')";
			qp.processQuery(guestInsert);
			
			String reservationInsert = INSERT + " " + INTO + " " + GUEST_RESERVATION + " " + VALUES + " ('" + reservationID + "', '" + gName + "', " + roomNumber + ", " + numberOfGuests + ", '" + startDate + "', '" + endDate + "', '" + gEmail + "')";
			qp.processQuery(reservationInsert);
			System.out.println("Done creating reservation.");
		
		}
		
		//if guest create reservation (number of guests) 
		//if not guest create guest (phone, cc,address)
		
	}

	private void listContactInfo(Scanner in, QueryProcessor qp){
		System.out.println("Enter a guest name:");
		String name = in.nextLine();
		String sqlString = SELECT + " Email, Phone_num, Address " + FROM + " " +
				GUEST + " " + WHERE + " G_name = \'" + name + "\'";
		qp.processQuery(sqlString);
		System.out.println();
	}

	private void listAllGuests(QueryProcessor qp){
		String sqlString = SELECT + " G_name, Email, Phone_num " + FROM + " "
				+ GUEST + " " + ORDER_BY + " G_name";
		qp.processQuery(sqlString);
		System.out.println();
	}

	private void listGuestsStayingOnDate(Scanner in, QueryProcessor qp){
		try {
			System.out.println("Enter date in DD-MM-YYYY format");
			String date = in.nextLine();
			String sqlDate = date.substring(6) + date.substring(2, 6) + date.substring(0, 2);
			String sqlString = SELECT + " G_name " + FROM + " " + GUEST_RESERVATION + " " +
					" " + WHERE + " Start_Date <= \'" + sqlDate + "\' AND End_Date >= " +
					"\'" + sqlDate + "\'";
			qp.processQuery(sqlString);
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("Not a valid date");
		}
	}

	private void listGuestsArrivingOnDate(Scanner in, QueryProcessor qp){
		try {
			System.out.println("Enter date in DD-MM-YYYY format");
			String date = in.nextLine();
			String sqlDate = date.substring(6) + date.substring(2,6) + date.substring(0,2);
			String sqlString = SELECT + " G_name " + FROM + " " + GUEST_RESERVATION + " " +
					" " + WHERE + " Start_Date = \'" + sqlDate + "\'";
			qp.processQuery(sqlString);
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("Not a valid date");
		}
	}
	
	private boolean guestHasReservation(String guestName, String guestEmail, QueryProcessor qp) {
		String guestSQL = SELECT + " G_name " + FROM + " " + GUEST + " " + WHERE + " Email='" + guestEmail + "' " + AND + " G_name='" 
																					+ guestName + "';";
		ResultSet guestResult = qp.processForResultSet(guestSQL);
		if (guestResult == null)
			return false;
		
		return true;
	}
	
	private String formatDate(String date) {
		String[] tokens = date.split("-");
		String formattedDate = tokens[2] + "-" + tokens[1] + "-" + tokens[0];
		return formattedDate;
	}
	
	
		
}
	
