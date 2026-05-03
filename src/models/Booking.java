package models;

import java.time.LocalDate;

public class Booking {
    private final String id;
    private final String guestUsername;
    private final String stayName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int numberOfGuests;
    private BookingState state;
    private int totalPrice;

    public Booking(String id, String guestUsername, String stayName,
                   LocalDate fromDate, LocalDate toDate, int numberOfGuests, int totalPrice) {
        this.id = id;
        this.guestUsername = guestUsername;
        this.stayName = stayName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfGuests = numberOfGuests;
        this.state = BookingState.REQUESTED;
        this.totalPrice = totalPrice;
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getGuestUsername() { return guestUsername; }
    public String getStayName() { return stayName; }
    public LocalDate getFromDate() { return fromDate; }
    public LocalDate getToDate() { return toDate; }
    public int getNumberOfGuests() { return numberOfGuests; }
    public BookingState getState() { return state; }
    public void setState(BookingState state) { this.state = state; }
    public int getTotalPrice() { return totalPrice; }
}
