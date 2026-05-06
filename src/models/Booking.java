package models;

import models.enums.BookingState;

public class Booking {
    private final String id;
    private final String guestUsername;
    private final String stayName;
    private ThirtyDayDate fromDate;
    private ThirtyDayDate toDate;
    private int numberOfGuests;
    private BookingState state;
    private int totalPrice;

    public Booking(String id, String guestUsername, String stayName,
                   ThirtyDayDate fromDate, ThirtyDayDate toDate, int numberOfGuests, int totalPrice) {
        this.id = id;
        this.guestUsername = guestUsername;
        this.stayName = stayName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfGuests = numberOfGuests;
        this.state = BookingState.REQUESTED;
        this.totalPrice = totalPrice;
    }

    public String getId() { return id; }
    public String getGuestUsername() { return guestUsername; }
    public String getStayName() { return stayName; }
    public ThirtyDayDate getFromDate() { return fromDate; }
    public ThirtyDayDate getToDate() { return toDate; }
    public int getNumberOfGuests() { return numberOfGuests; }
    public BookingState getState() { return state; }
    public void setState(BookingState state) { this.state = state; }
    public int getTotalPrice() { return totalPrice; }
}
