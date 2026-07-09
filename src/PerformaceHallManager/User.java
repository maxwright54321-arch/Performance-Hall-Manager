package PerformaceHallManager;

public class User { //returns users details used to logging in
	private int userID;
	private String username;
	private String name;
	private String houseNumber;
	private String postcode;
	private String city;
	private String role;
	
	public User(int userID, String username, String name, String houseNumber, String postcode, String city, String role) {
		this.userID = userID;
		this.username = username;
		this.houseNumber = houseNumber;
		this.postcode = postcode;
		this.city = city;
		this.role = role;
		this.name = name;
	}
	
	public String getUsername() { //returns the username of the user
		return username;
	}
	
	public String getRole() { //returns the role - admin/user
		return role;
	}
	
	public String getFullAddress() { //returns the address of the user
		return houseNumber + ", " + postcode + ", " + city; 
	}
	
	public String getName() { //returns the name of the user
		return name;
	}
	
	public int getUserID() { //returns the ID number of the user
		return userID;
	}
}
