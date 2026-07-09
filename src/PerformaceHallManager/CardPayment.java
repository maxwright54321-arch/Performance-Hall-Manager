package PerformaceHallManager;

public class CardPayment implements PaymentMethod { //allows the user to pay by card

	private String cardNumber;
	private String securityCode;
	
	public CardPayment(String cardNumber, String securityCode) {
		this.cardNumber = cardNumber;
		this.setSecurityCode(securityCode);
	}
	
	public Receipt processPayment(double amount, Address fullAddress) { //specifies the payment type for the receipt
		String details = "by Credit Card: " + cardNumber;
		return new Receipt(amount, details, fullAddress.getFullAddress());
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

}
