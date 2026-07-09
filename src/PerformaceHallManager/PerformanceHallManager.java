package PerformaceHallManager;
import java.util.Scanner;
import java.util.ArrayList;

public class PerformanceHallManager { //the main app for the Performance hall, used to call other classes
	
	private StockManager stockManager;
	private Basket basket;
	private Scanner scanner;
	private UserManager userManager;
	private User currentUser;
	
	public PerformanceHallManager() {
		//imports classes for use
		this.stockManager = new StockManager();
		this.basket = new Basket();
		this.scanner = new Scanner(System.in);
		this.userManager = new UserManager();
	}
	
	public void start() {
		System.out.println("App has started");
		stockManager.loadStockFromFile(); //loading stock from stock.txt
		System.out.println("Welcome to performance hall manager\nPlease select your username:");
		while (true) {
			login();
			
			boolean loggedIn = true;
			while (loggedIn) { //continuously runs the main menu until the user exits or logs out
				if (currentUser.getRole().equals("admin")) { //checks which menu to display - user or admin
					printAdminMenu();
					String choice = scanner.nextLine().trim(); //takes input from the user on which task the admin would like to perform
					switch (choice) {
					case "1":
						displayStockWithFee();
						break;
					case "2":
						addNewEvent();
						break;
					case "3":
						loggedIn = false;
						break;
					case "4":
						exit();
						return;
					default:
						System.out.println("Input not valid");		
					}
				}
				else {
				printMainMenu();
				String choice = scanner.nextLine().trim(); //takes input from the user on which task they would like to perform
				switch (choice) {
				case "1":
					displayStock();
					break;
				case "2":
					addToBasket();
					break;
				case "3":
					basket.viewBasket();
					break;
				case "4":
					checkout();
					break;
				case "5":
					loggedIn = false;
					return;
				case "6":
					exit();
					return;
				default:
					System.out.println("Invalid choice. try again.");
					}
				}
			}
		}
	}
		
			
	private void printMainMenu() { //displays a main menu to be viewed by users
		System.out.println("\nMenu:");
		System.out.println("1. View Available events");
		System.out.println("2. Add ticket to basket");
		System.out.println("3. View Basket");
		System.out.println("4. Checkout");
		System.out.println("5. Logout");
		System.out.println("6. Exit");
		System.out.println("Choose an option 1-5.");
	}
	
	private void displayStock() { //calls the stock manager class to display all available items to the user
		ArrayList<LiveEvent> stock = stockManager.getStock();
		for (LiveEvent event : stock) {
			System.out.println(event);
		}
	}
	
	private void addToBasket() { //calls the basket class to add one or several tickets to the basket
		System.out.println("Enter the event ID number you would like to purchase a ticket for: "); //confirms the ID number for the show the user wants to buy a ticket(s) for
		try {
			int eventID = Integer.parseInt(scanner.nextLine().trim());
			
			LiveEvent selected = null;
			for (LiveEvent event : stockManager.getStock()) {
				if (event.getEventID() == eventID) { //checks for a match in ID numbers
					selected = event;
					break;
				}
			}
			
			if (selected == null) {
				System.out.println("Event not found");
				return;
			}
			
			System.out.println("Enter number of tickets: "); //checks how many tickets the user wants to buy
			int quantity = Integer.parseInt(scanner.nextLine().trim());
			basket.addTickets(selected, quantity);
		}
		catch (NumberFormatException e) { //makes sure the input is valid
			System.out.println("Invalid input");
		}
	}
	
	private void checkout() { //allows the user to finalise the purchase of their tickets
		if (basket.isEmpty()) { //checks if the users basket is empty
			System.out.println("Your basket is empty");
			return;
		}
		//gets the users address details
		System.out.print("Enter house number: ");
		String house = scanner.nextLine();
		System.out.println("Enter city: ");
		String city = scanner.nextLine();
		System.out.println("Enter postcode: ");
		String postcode = scanner.nextLine();
		Address address = new Address(house, city, postcode);
		
		//gets the user to chose their payment method
		System.out.println("Select payment method - card or paypal");
		String method = scanner.nextLine().trim().toLowerCase();
		
		PaymentMethod payment = null;
		
		if (method.equals("card")) { //gets the card number and CCV if the user chooses to pay by card
			System.out.println("Enter 6 digit card number: ");
			String cardNumber = scanner.nextLine().trim();
			if (!cardNumber.matches("\\d{6}")) { //checks the card number is 6 digits
				System.out.println("Invalid card number, it must be 6 digits");
				return;
			}
			
			System.out.println("Enter 3 digit CCV code: ");
			String CCV = scanner.nextLine().trim();
			if(!CCV.matches("\\d{3}")) { //checks the CCv is 3 digits
				System.out.println("Invalid CCV, it must be 3 digits");
				return;
			}
			payment = new CardPayment(cardNumber, CCV); //finalises the payment, so it can be displayed in the receipt
		}
		
		else if (method.equals("paypal")) { //gets the users email if they choose to pay by Paypal
			System.out.println("Enter Paypal email: ");
			String email = scanner.nextLine().trim();
			payment = new PayPal(email);
		}
		
		else { //checks a valid payment type was entered
			System.out.println("Unsupported payment method.");
			return;
		}
		
		Receipt receipt = basket.pay(payment, address); //creates the receipt using the receipt class
		System.out.println("\n Payment successful. Receipt: " + receipt); //confirms payment to user and displays receipt

	}
	private void printAdminMenu() { //displays a main menu to be viewed by admins
		System.out.println("\nAdmin menu:");
	    System.out.println("1. View all events (with performance fee)");
	    System.out.println("2. Add new event");
	    System.out.println("3. Logout");
	    System.out.println("4. Exit");
	    System.out.print("Choose option 1–3: ");
	}
	
	private void displayStockWithFee() { //adds the performance fee to the event so admins can view the cost of the event
		for (LiveEvent event : stockManager.getStock()) {
			System.out.println(event + " | Performance Fee: £" + event.getPerformanceFee());
		}
	}
	
	private void addNewEvent() { //allows admins to create new events by calling on stockManager class
		try {
			System.out.println("Enter an ID number for the new event: "); //gets input from the admin on the new events ID
			int eventID = Integer.parseInt(scanner.nextLine().trim());
			
			for (LiveEvent event : stockManager.getStock()) {
				if (event.getEventID() == eventID) { //checks the eventId is not already in use
					System.out.println("that event ID is already in use");
					return;
				}
			}
			
			//gets all the inputs from the admin needed to create a new event
			System.out.print("Enter event name: ");
	        String name = scanner.nextLine().trim();

	        System.out.print("Enter age restriction (ALL or ADULTS): ");
	        AgeRestrictionCategory restriction = AgeRestrictionCategory.valueOf(scanner.nextLine().trim().toUpperCase());

	        System.out.print("Enter number of tickets: ");
	        int quantity = Integer.parseInt(scanner.nextLine().trim());

	        System.out.print("Enter performance fee: ");
	        double fee = Double.parseDouble(scanner.nextLine().trim());

	        System.out.print("Enter ticket price: ");
	        double price = Double.parseDouble(scanner.nextLine().trim());

	        System.out.print("Enter event category (MUSIC or PERFORMANCE): ");
	        LiveEventCategory category = LiveEventCategory.valueOf(scanner.nextLine().trim().toUpperCase());

	        LiveEvent newEvent = null;

	        if (category == LiveEventCategory.MUSIC) { //checks if it is a Music event, so that specific categories for Music events can be inputed
	            System.out.print("Enter event type (CONCERT or DJ_SET): ");
	            String type = scanner.nextLine().trim().toUpperCase();

	            System.out.print("Enter number of performers (bands or DJs): ");
	            int performers = Integer.parseInt(scanner.nextLine().trim());

	            newEvent = new MusicEvent(eventID, name, restriction, quantity, fee, price, category, type, performers); //creates the new MusicEvent
	        }
	        else if (category == LiveEventCategory.PERFORMANCE) { //checks if it is a Performance event, so that specific categories for Performance events can be inputed
	            System.out.print("Enter event type (THEATRE, COMEDY or MAGIC): ");
	            String type = scanner.nextLine().trim().toUpperCase();

	            System.out.print("Enter performance language: ");
	            String language = scanner.nextLine().trim();

	            newEvent = new PerformanceEvent(eventID, name, restriction, quantity, fee, price, category, type, language); //creates the new PerformanceEvent
	        }

	        if (newEvent != null) {
	            stockManager.getStock().add(newEvent); //adds the new event to the array
	            stockManager.saveStock(); //saves the new event to Stock.txt
	            System.out.println("Event added successfully.");
	        } else { //checks if the event was added successfully
	            System.out.println("New Event failed.");
	        }

	    } catch (Exception e) { //checks there was no error making the event and displays the cause
	        System.out.println("Error creating event: " + e.getMessage());
		}
	}
	
	private void login() { //provides a recallable login function so users and admins can log out and back in
		System.out.println("Users:");
		userManager.printUsernames(); //Outputs all usernames so one can be chosen by the user
		
		User user = null;
		while (user == null) {
			System.out.println("Enter username: "); //asks the user to input a username
			String username = scanner.nextLine().trim();
			user = userManager.login(username);
			if (user == null) { //checks the username matches one on UserAccounts.txt
				System.out.println("Invalid username, try again.");
			}		
		}
		this.currentUser = user;
				System.out.println("Logged in as: " + user.getName() + " (" + user.getRole() + ")"); //confirms to the user that they have logged in
	}
	
	private void exit() { //allows the program to be exited and esures stock is saved
		System.out.println("Saving and exiting");
		stockManager.saveStock();
		scanner.close();
	}
	
	public static void main(String[] args) { //used to run the app on
		System.out.println("Starting app");
		PerformanceHallManager app = new PerformanceHallManager();
		app.start();
	}
}
