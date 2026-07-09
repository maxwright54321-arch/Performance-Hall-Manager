package PerformaceHallManager;

public class MusicEvent extends LiveEvent { //specifies qualities exclusively to Music events
	private String type;
	private int numberOfPerformers;

	public MusicEvent(int eventID, String eventName, AgeRestrictionCategory restriction,
					 int quantityInStock, double performanceFee, double ticketPrice, LiveEventCategory liveEventCategory, String type, int numberOfPerformers) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, liveEventCategory); //uses the pre-defined variables in LiveEvent abstract class
		this.type = type;
		this.numberOfPerformers = numberOfPerformers;
	}
	
	public String getEventType() { //returns type of Music event
		return type;
	}
	
	public int getNumberOfPerformers() { //returns the number of performers in the Music event
		return numberOfPerformers;
	}

	public String toString() { //formats the Music event into a easily and clearly organised string for the user and admin to view
	    return String.format("%s - ID: %d | Name: %s | Performers: %d | Price: £%.2f | Tickets: %d | Age: %s",
	            type,
	            getEventID(),
	            getEventName(),
	            numberOfPerformers,
	            getTicketPrice(),
	            getQuantityInStock(),
	            getAgeRestrictionCategory());
	}
}
