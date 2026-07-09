package PerformaceHallManager;
import java.io.*;
import java.util.*;

public class UserManager { //loads and prints users from UserAccounts.txt
	private List<User> users;

		public UserManager() {
			this.users = new ArrayList<>();
			loadUsers();
		}
		
		private void loadUsers() { //loads users from UserAccounts.txt
			try (BufferedReader reader = new BufferedReader(new FileReader("UserAccounts.txt"))) {
				String line;
				while ((line = reader.readLine()) != null) { //reads each line individually until the line is blank
					String[] parts = line.split(",");
					
					if (parts.length !=7) { //checks there are 7 fields in the user's data
						continue;
					}
					int id = Integer.parseInt(parts[0].trim());
					String username = parts[1].trim();
	                String name = parts[2].trim();
	                String houseNumber = parts[3].trim();
	                String postcode = parts[4].trim();
	                String city = parts[5].trim();
	                String role = parts[6].trim().toLowerCase();
	                
	                users.add(new User(id, username, name, houseNumber, postcode, city, role)); //adds a user to the array
				}
			}
			catch (IOException e) { //ensures a user is notified if the users weren't loaded correctly
				System.out.println("Error loading users");
			}
		}
		public void printUsernames() { //outputs all usernames to the user
			for(User user : users) {
				System.out.println(user.getUsername());
			}
		}
		public User login(String username) { //allows the user to login
			for (User user : users) {
				if (user.getUsername().equalsIgnoreCase(username)) {
					return user;
				}
			}
			return null;
		}
}
