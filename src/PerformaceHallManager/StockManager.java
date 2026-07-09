package PerformaceHallManager;
import java.io.*;
import java.util.ArrayList;


public class StockManager { //allows the system to load, save and display stock for stock.txt
	private ArrayList<LiveEvent> stock;
	private final String filePath = "Stock.txt"; //defining the filepath
	
	public StockManager() { //creates the array in which all stock is stored
		this.stock = new ArrayList<>();
	}
	
	public void loadStockFromFile() { //loads stock from stock.txt
	    BufferedReader reader = null;

	    try {
	        reader = new BufferedReader(new FileReader(filePath)); //allows the file to be read
	        String line;

	        while ((line = reader.readLine()) != null) { //goes through each individual line, splitting at the commas so all the right fields can be stored for each event
	            String[] parts = line.split(",");

	            if (parts.length != 9) { //ensures that all data in the file Stock.txt is in the correct format
	                System.out.println("Invalid line: " + line);
	                continue;
	            }

	            try { //stores each piece of data in its respective category for that event
	                int eventID = Integer.parseInt(parts[0].trim());
	                LiveEventCategory category = LiveEventCategory.valueOf(parts[1].trim().toUpperCase());
	                String eventType = parts[2].trim().toUpperCase();
	                String eventName = parts[3].trim();
	                AgeRestrictionCategory restriction = AgeRestrictionCategory.valueOf(parts[4].trim().toUpperCase());
	                int quantity = Integer.parseInt(parts[5].trim());
	                double performanceFee = Double.parseDouble(parts[6].trim());
	                double ticketPrice = Double.parseDouble(parts[7].trim());
	                String additionalInfo = parts[8].trim();

	                LiveEvent event = null;

	                if (category == LiveEventCategory.MUSIC) { //checks if the event is Music and passes the additional information as the number of performers
	                    int performers = Integer.parseInt(additionalInfo);
	                    event = new MusicEvent(eventID, eventName, restriction, quantity, performanceFee, ticketPrice, category, eventType, performers); //Adds the event to the array
	                } 
	                else if (category == LiveEventCategory.PERFORMANCE) { //checks if the event is Performance and passes the additional information as the language the event is in
	                    event = new PerformanceEvent(eventID, eventName, restriction, quantity, performanceFee, ticketPrice, category, eventType, additionalInfo); //adds the event to the array
	                }

	                if (event != null) { //checks the event was made properly
	                    stock.add(event);
	                } 
	                else { //informs the user if the event was not made properly
	                    System.out.println("Did not recognis event type: " + eventType);
	                }

	            } catch (Exception e) { //informs the user if the reader cannot process a line	                
	            	System.out.println("Error reading line: " + line);
	                System.out.println("due to" + e.getMessage());
	            }
	        }

	    } 
	    catch (IOException e) { //checks for any errors when reading the file
	        System.out.println("Error reading stock file: " + e.getMessage());
	    } 
	    finally { //closes the reader at the end of the file
	        try {
	            if (reader != null) reader.close();
	        } 
	        catch (IOException e) { //informs the user if the reader fails to close
	            System.out.println("Error closing reader: " + e.getMessage());
	        }
	    }
	}

	
	public void saveStock() { //allows stockt to be saved to Stock.txt
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (LiveEvent event : stock) { //cycles through each event
				String eventType = "";
				String additionalInfo = "";
				
				if (event instanceof MusicEvent) { //if the event is Music it writes it as a music event with the number of performers
					MusicEvent music = (MusicEvent) event;
					eventType = music.getEventType();
					additionalInfo = String.valueOf(music.getNumberOfPerformers());
				}
				else if (event instanceof PerformanceEvent) { //if the event is a Performance it writes it as a Performance event with the language 
					PerformanceEvent performance = (PerformanceEvent) event;
					eventType = performance.getEventtype();
					additionalInfo = performance.getLanguage();
				}
				
				//formats the data to by written to the Stock.txt file
				String line = String.format("%d,%s,%s,%s,%s,%d,%.2f,%.2f,%s",
						event.getEventID(),
						event.getEventCategory().name(),
						eventType,
						event.getEventName(),
						event.getAgeRestrictionCategory().name(),
						event.getQuantityInStock(),
						event.getPerformanceFee(),
						event.getTicketPrice(),
						additionalInfo);
				writer.write(line);
				writer.newLine(); //ensures each event has its own line
			}
		}
		catch (IOException e) { //checks for errors when writing to the file
			System.out.println("Error writing to stock file: " + e.getMessage());
		}
	}
	public ArrayList<LiveEvent> getStock() { //returns the stock array
		return stock;
	}
}
