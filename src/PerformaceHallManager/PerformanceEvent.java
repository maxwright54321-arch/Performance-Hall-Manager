package PerformaceHallManager;

public class PerformanceEvent extends LiveEvent{ //specifies qualities exclusive to performance events
	private String type;
	private String language;
	
	public PerformanceEvent(int eventID, String eventName, AgeRestrictionCategory restriction,
							int quantityInStock, double performanceFee, double ticketPrice, LiveEventCategory liveEventCategory, String type, String language) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, liveEventCategory); //uses the pre-defined variables in the abstract LiveEvent class
		this.type = type;
		this.language = language;
	}

	public String getEventtype() { //returns the type of Performance event
		return type;
	}
	
	public String getLanguage() { //returns the language the performance event is in
		return language;
	}
	
    public String toString() { //formats the Music event into a easily and clearly organised string for the user and admin to view
        return String.format("%s - ID: %d | Name: %s | Language: %s | Price: £%.2f | Tickets: %d | Age: %s",
                type,
                getEventID(),
                getEventName(),
                language,
                getTicketPrice(),
                getQuantityInStock(),
                getAgeRestrictionCategory());
    }
}
