package PerformaceHallManager;
 
public abstract class LiveEvent { //abstract LiveEvent allows framework for Music and Performance events
	private int eventID;
	private String eventName;
	private LiveEventCategory liveEventCategory;
	private double performanceFee;
	private int quantityInStock;
	private AgeRestrictionCategory restriction;
	private double ticketPrice;
	
	public LiveEvent(int eventID, String eventName, AgeRestrictionCategory restriction,
					 int quantityInStock, double performanceFee, double ticketPrice, LiveEventCategory liveEventCategory) {
		this.eventID = eventID;
		this.eventName = eventName;
		this.restriction = restriction;
		this.quantityInStock = quantityInStock;
		this.performanceFee = performanceFee;
		this.ticketPrice = ticketPrice;
		this.liveEventCategory = liveEventCategory;
	}
	
	public int getEventID() { //returns the eventID of an event
		return eventID;
	}
	public String getEventName() { //returns the name of an event
		return eventName;
	}
	public AgeRestrictionCategory getAgeRestrictionCategory() { //returns the Age category of an event
		return restriction;
	}
	public int getQuantityInStock() { //returns the amount of tickets left for an event
		return quantityInStock;
	}
	public double getPerformanceFee() { //returns the performance fee of an event
		return performanceFee;
	}
	public double getTicketPrice() { //returns the ticket price of an event
		return ticketPrice;
	}
	public LiveEventCategory getEventCategory() { //returns the category of an event (Music or Performance)
		return liveEventCategory;
	}

    public void setQuantitiyInStock(int newQuantityInStock) { //allows the quantity of tickets in stock to be set. Used when making new events
        this.quantityInStock = newQuantityInStock;
    }


    public abstract String toString();
}
