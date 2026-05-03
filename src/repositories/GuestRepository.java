package repositories;

import models.users.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepository {
    private static GuestRepository instance;

    private List<Guest> guests;

    private GuestRepository() {
        this.guests = new ArrayList<>();
    }

    public static GuestRepository getInstance() {
        if (instance == null) instance = new GuestRepository();
        return instance;
    }

    public void addGuest(Guest newGuest) {
        guests.add(newGuest);
    }

    public List<Guest> getAllGuests() {
        return new ArrayList<>(guests);
    }

    public Guest getGuestByName(String name) {
        return guests.stream()
                .filter(guest -> guest.getUsername().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Guest getGuestByEMail(String email) {
        return guests.stream()
                .filter(guest -> guest.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
