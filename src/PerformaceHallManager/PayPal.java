package PerformaceHallManager;

public class PayPal implements PaymentMethod { //allows paypal to be used to pay for tickets
	
	private String email;
	
	public PayPal(String email) {
		this.email = email;
	}
	
	public Receipt processPayment(double amount, Address fullAddress) { //Specifies the payment type for the receipt
		String details = "by PayPal: " + email;
		return new Receipt(amount, details, fullAddress.getFullAddress());
	}
}
