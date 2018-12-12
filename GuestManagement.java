import java.util.Scanner;

public class GuestManagement extends MainMenu {
	private int fCurrentSelection;

	public GuestManagement() {
		fCurrentSelection = 0;
	}

	public void outputMenu() {
		System.out.println("GUEST MANAGEMENT MENU");
		System.out.println("\t1. Create a reservation for a new guest");
		System.out.println("\t2. List guest contact information");
		System.out.println("\t3. List all guests in alphabetical order");
		System.out.println("Please make a selection. Enter -1 to return to the Main Menu:");
	}
	
	public void run(Scanner in, QueryProcessor qp) {
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
			} else if (fCurrentSelection == 4) {
				System.out.println("LIST ALL GUEST ARRIVING ON DATE:");
				listGuestsArrivingOnDate(in, qp);
			}

			if (fCurrentSelection > 5 || fCurrentSelection == 0 || fCurrentSelection < -1)
				System.out.println("Your input was incorrect! Please try again.");

		} while(fCurrentSelection != -1);
	}

	private void createReservation(Scanner in, QueryProcessor qp){

	}

	private void listContactInfo(Scanner in, QueryProcessor qp){

	}

	private void listAllGuests(QueryProcessor qp){

	}

	private void listGuestsStayingOnDate(Scanner in, QueryProcessor qp){

	}

	private void listGuestsArrivingOnDate(Scanner in, QueryProcessor qp){
		
	}
}
