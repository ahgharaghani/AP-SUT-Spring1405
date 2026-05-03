package repositories;

import models.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingRepository {
    private static BookingRepository instance;

    private List<Booking> bookings;

    private BookingRepository() {
        this.bookings = new ArrayList<>();
    }

    public static BookingRepository getInstance() {
        if (instance == null) instance = new BookingRepository();
        return instance;
    }

    public void addBooking(Booking newBooking) {
        bookings.add(newBooking);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    public List<Booking> getBookingsByStayName(String name) {
        return bookings.stream()
                .filter(booking -> booking.getStayName().equals(name))
                .collect(Collectors.toList());
    }

    public Booking getBookingByID(String bookingID) {
        return bookings.stream()
                .filter(booking -> booking.getId().equals(bookingID))
                .findFirst()
                .orElse(null);
    }
}
