package PerformaceHallManager;
import java.time.LocalDate;

public class Receipt { //uses Address, PayPal and CardPayment to produce a receipt that can be read by the user
	private double amount;
	private String paymentDetails;
	private String address;
	private LocalDate date;
	
	public Receipt(double amount, String paymentDetails, String address) {
		this.amount = amount;
		this.paymentDetails = paymentDetails;
		this.address = address;
		this.date = LocalDate.now(); //used to display the date for the receipt
	}

	
	public String toString() { //formats the receipt
		return amount + " paid using " + paymentDetails + " on " + date + " from " + address;
	}
}
