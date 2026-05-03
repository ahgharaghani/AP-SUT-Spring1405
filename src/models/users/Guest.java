package models.users;

import models.Booking;
import java.util.ArrayList;
import java.util.List;

public class Guest extends User {
    private List<Booking> bookings;

    public Guest(String username, String password, String email) {
        super(username, password, email);
        this.bookings = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "guest";
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}
