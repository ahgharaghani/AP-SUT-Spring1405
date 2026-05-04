package models;

import models.enums.CancellationPolicy;

import java.util.ArrayList;
import java.util.List;

public class Stay {
    private String name;
    private String city;
    private String address;
    private int capacity;
    private int pricePerNight;
    private CancellationPolicy policy;
    private String hostUsername;
    private boolean active;

    List<Booking> bookings;

    public Stay(String name, String city, String address, int capacity,
                int pricePerNight, CancellationPolicy policy, String hostUsername) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.policy = policy;
        this.hostUsername = hostUsername;
        this.active = true;

        this.bookings = new ArrayList<>();
    }

    // Getters & Setters
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public int getCapacity() { return capacity; }
    public int getPricePerNight() { return pricePerNight; }
    public CancellationPolicy getPolicy() { return policy; }
    public String getHostUsername() { return hostUsername; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public List<Booking> getBookings() {
        return bookings;
    }
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}
