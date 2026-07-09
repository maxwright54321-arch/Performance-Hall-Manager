package PerformaceHallManager;
import java.util.*;

public class Basket { //allows event tickets to add and clear tickets from their basket as well as view current items
	private Map<LiveEvent, Integer> basketItems;
	
	public Basket() {
		this.basketItems = new LinkedHashMap<>();
	}
	
	public void addTickets(LiveEvent event, int quantity) { //Allows tickets to be added to the basket
		if (event.getQuantityInStock() < quantity) { //check if there are enough tickets available
			System.out.println("Not enough tickets in stock");
		}
		else {
			basketItems.put(event, basketItems.getOrDefault(event, 0) + quantity);
			System.out.println(quantity + " tikets for " + event + " were added to your basket");
		}
	}
	
	public void clearBasket() { //Allows the basket to be cleared
		basketItems.clear();
	}
	
	public void viewBasket() { //Allows the contents of the basket to be checked
		if (basketItems.isEmpty()) { //Checks if there are any items in the basket
			System.out.println("Your basket is empty");
		}
		else {
			System.out.println("Basket:");
			for (Map.Entry<LiveEvent, Integer> entry : basketItems.entrySet()) {
				LiveEvent event = entry.getKey();
				int quantity = entry.getValue();
				System.out.println(event.getEventName() + " x" + quantity + " costing £" + (event.getTicketPrice() * quantity)); //prints all the tickets in useres basket and the cost
			}
			System.out.println("Your total is: £" + calculateTotal()); //outputs total
		}
	}
	public double calculateTotal() { //Allows the total sum of ticket costs from the basket to be calculated
		double total = 0.0;
		for (Map.Entry<LiveEvent, Integer> entry : basketItems.entrySet()) {
			total += entry.getKey().getTicketPrice() * entry.getValue();
		}
		return total;
	}
	
	public Receipt pay(PaymentMethod paymentMethod, Address fullAddress) { //allows a readable receipt to be viewed by the user
		double total = calculateTotal();
		Receipt receipt = paymentMethod.processPayment(total, fullAddress); //uses the receipt class to create a readable receipt for the user
		for (Map.Entry<LiveEvent, Integer> entry : basketItems.entrySet()) {
			LiveEvent event = entry.getKey();
			int ticketsBrought = entry.getValue();
			int newStock = event.getQuantityInStock() - ticketsBrought;
			event.setQuantitiyInStock(newStock); //takes stock away
		}
		clearBasket(); //clears basket after tickets have been brought
		return receipt;
	}
	
	public boolean isEmpty() { //checks if the basket is empty but no output for the user
		return basketItems.isEmpty();
	}
}
