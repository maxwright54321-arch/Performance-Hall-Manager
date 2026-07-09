package PerformaceHallManager;

public class Address { //Allows addresses to be made and used in receipts
	private String street;
	private String city;
	private String postcode;

	
	public Address(String street, String city, String postcode) {
		this.street = street;
		this.city = city;
		this.postcode = postcode;
	}
	
	public String getFullAddress() { //returns the full address inputed to be used in receipts
		return street + ", " + city + ", " + postcode;
	}
}
