package controllers;

import dto.BookingHostDTO;
import dto.StayStatsDTO;
import models.*;
import models.users.Guest;
import models.users.Host;

import java.util.ArrayList;
import java.util.List;

public class HostController {

    private static boolean validateCurrentMenu() {
        return App.getCurrentMenu() == Menu.HOST;
    }

    public static String getBalance() {
        if (!App.isLoggedIn() || !App.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Host host = (Host) App.getLoggedInUser();
        return "your balance: $" + host.getBalance();
    }

    public static String addStay(String name, String city, String address, String capStr, String ppnStr, String policyStr) {
        if (!App.isLoggedIn() || !App.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        if (name.length() < 3) return "stay name is too short.";
        if (Repository.getStayByName(name) != null) return "stay name already exists.";

        int capacity;
        try {
            capacity = Integer.parseInt(capStr);
            if (capacity <= 0) {
                return "invalid capacity.";
            }
        } catch (NumberFormatException e) {
            return "invalid capacity.";
        }

        int ppn;
        try {
            ppn = Integer.parseInt(ppnStr);
            if (ppn <= 0) {
                return "invalid price per night.";
            }
        } catch (NumberFormatException e) {
            return "invalid price per night.";
        }

        CancellationPolicy policy;
        if (policyStr.equals("flexible")) policy = CancellationPolicy.FLEXIBLE;
        else if (policyStr.equals("moderate")) policy = CancellationPolicy.MODERATE;
        else if (policyStr.equals("strict")) policy = CancellationPolicy.STRICT;
        else return "invalid cancellation policy.";

        Host host = (Host) App.getLoggedInUser();
        Stay stay = new Stay(name, city, address, capacity, ppn, policy, host.getUsername());
        host.addStay(stay);
        Repository.addStay(stay);

        return "stay \"" + name + "\" added successfully.";
    }

    public static List<StayStatsDTO> getCurrentHostStays() {
        if (!App.isLoggedIn() || !App.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Host host = (Host) App.getLoggedInUser();
        List<Stay> stays = host.getStays();
        if (stays.isEmpty()) return null;
        List<StayStatsDTO> stayDTOs = new ArrayList<>();

        for (Stay stay : stays) {
            String status = stay.isActive() ? "active" : "inactive";
            String policyName = stay.getPolicy().getPolicyName();

            StayStatsDTO dto = new StayStatsDTO(
                    stayDTOs.size() + 1,
                    stay.getName(),
                    stay.getCity(),
                    stay.getAddress(),
                    stay.getCapacity(),
                    stay.getPricePerNight(),
                    policyName,
                    status
            );
            stayDTOs.add(dto);
        }

        return stayDTOs;
    }

    public static String deactivateStay(String name) {
        if (!App.isLoggedIn() || !App.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Stay stay = Repository.getStayByName(name);
        if (stay == null) return "stay not found.";
        if (!stay.isActive()) return "stay is already inactive.";

        stay.setActive(false); return "stay \"" + stay.getName() + "\" updated successfully.";
    }

    public static String activateStay(String name) {
        if (!App.isLoggedIn() || !App.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Stay stay = Repository.getStayByName(name);
        if (stay == null) return "stay not found.";
        if (stay.isActive()) return "stay is already active.";

        stay.setActive(true); return "stay \"" + stay.getName() + "\" updated successfully.";
    }

    public static List<BookingHostDTO> listBookingReqs() {
        if (!App.isLoggedIn() || !App.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Host host = (Host) App.getLoggedInUser();
        List<Stay> stays = host.getStays();
        List<BookingHostDTO> bookingDTOs = new ArrayList<>();

        if (stays.isEmpty()) return null;

        for (Stay stay : stays) {
            List<Booking> bookings = Repository.getBookingsByStayName(stay.getName());
            for (Booking booking : bookings) {
                if (booking.getState() == BookingState.REQUESTED) {
                    BookingHostDTO dto = new BookingHostDTO(
                        bookingDTOs.size() + 1,
                        booking.getId(),
                        booking.getStayName(),
                        booking.getGuestUsername(),
                        booking.getFromDate().toString(),
                        booking.getToDate().toString(),
                        booking.getNumberOfGuests()
                    );
                    bookingDTOs.add(dto);
                }
            }
        }

        return bookingDTOs;
    }

    public static String approveBooking(String bookingID) {
        if (!App.isLoggedIn() || !App.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Booking booking = Repository.getBookingByID(bookingID);
        if (booking == null) return "booking not found.";
        if (booking.getState() != BookingState.REQUESTED) return "invalid booking status.";

        List<Booking> otherBookings = Repository.getBookingsByStayName(booking.getStayName());
        for (Booking b : otherBookings) {
            if (b.getState() == BookingState.CONFIRMED &&
                    (!booking.getFromDate().isBefore(b.getToDate()) && !b.getToDate().isAfter(booking.getFromDate())))
                return "dates are not available.";
        }

        booking.setState(BookingState.CONFIRMED);
        /* Guest guest = guestRepo.getGuestByName(booking.getGuestUsername());
        guest.addBooking(booking); */
        Host host = (Host) App.getLoggedInUser();
        host.addBalance(booking.getTotalPrice());
        return "booking " + bookingID + " confirmed successfully.";
    }

    public static String rejectBooking(String bookingID) {
        if (!App.isLoggedIn() || !App.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");
        Booking booking = Repository.getBookingByID(bookingID);
        if (booking == null) return "booking not found.";
        if (booking.getState() != BookingState.REQUESTED) return "invalid booking status.";

        booking.setState(BookingState.REJECTED);
        Guest guest = Repository.getGuestByName(booking.getGuestUsername());
        guest.addBalance(booking.getTotalPrice());
        return "booking " + bookingID + " rejected successfully.";
    }
}
