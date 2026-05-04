package models;

import models.users.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private static final List<Booking> bookingsRepo = new ArrayList<>();
    private static final List<Stay> stayRepo = new ArrayList<>();
    private static final List<Host> hostRepo = new ArrayList<>();
    private static final List<Guest> guestRepo = new ArrayList<>();


    /* Booking */
    public static void addBooking(Booking newBooking) {
        bookingsRepo.add(newBooking);
    }

    public static List<Booking> getAllBookings() {
        return new ArrayList<>(bookingsRepo);
    }

    public static List<Booking> getBookingsByStayName(String name) {
        return bookingsRepo.stream()
                .filter(booking -> booking.getStayName().equals(name))
                .collect(Collectors.toList());
    }

    public static Booking getBookingByID(String bookingID) {
        return bookingsRepo.stream()
                .filter(booking -> booking.getId().equals(bookingID))
                .findFirst()
                .orElse(null);
    }

    /* Guests */
    public static void addGuest(Guest newGuest) {
        guestRepo.add(newGuest);
    }

    public static List<Guest> getAllGuests() {
        return new ArrayList<>(guestRepo);
    }

    public static Guest getGuestByName(String name) {
        return guestRepo.stream()
                .filter(guest -> guest.getUsername().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static Guest getGuestByEmail(String email) {
        return guestRepo.stream()
                .filter(guest -> guest.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    /* Hosts */
    public static void addHost(Host newHost) {
        hostRepo.add(newHost);
    }

    public static List<Host> getAllHosts() {
        return new ArrayList<>(hostRepo);
    }

    public static Host getHostByName(String name) {
        return hostRepo.stream()
                .filter(host -> host.getUsername().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static Host getHostByEmail(String email) {
        return hostRepo.stream()
                .filter(host -> host.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    /* Stays */
    public static void addStay(Stay newStay) {
        stayRepo.add(newStay);
    }

    public static List<Stay> getAllStays() {
        return new ArrayList<>(stayRepo);
    }

    public static Stay getStayByName(String name) {
        return stayRepo.stream()
                .filter(stay -> stay.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static List<Stay> getStaysByCity(String city) {
        return stayRepo.stream()
                .filter(stay -> stay.getCity().equals(city))
                .collect(Collectors.toList());
    }
}
