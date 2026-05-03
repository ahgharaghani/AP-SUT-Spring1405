package controllers;

import dto.BookingHostDTO;
import dto.StayDTO;
import models.*;
import models.policies.*;
import models.users.Guest;
import models.users.Host;
import repositories.BookingRepository;
import repositories.GuestRepository;
import repositories.HostRepository;
import repositories.StayRepository;

import java.util.ArrayList;
import java.util.List;

public class HostController {
    private static HostController instance;
    private GuestRepository guestRepo;
    private StayRepository stayRepo;
    private BookingRepository bookingRepo;
    private final App app;

    private HostController() {
        this.app = App.getInstance();
        this.guestRepo = GuestRepository.getInstance();
        this.stayRepo = StayRepository.getInstance();
        this.bookingRepo = BookingRepository.getInstance();
    }

    public static HostController getInstance() {
        if (instance == null) instance = new HostController();
        return instance;
    }

    private boolean validateCurrentMenu() {
        return app.getCurrentMenu() == Menu.HOST;
    }

    public String getBalance() {
        if (!app.isLoggedIn() || !app.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Host host = (Host) app.getLoggedInUser();
        return "your balance: $" + host.getBalance();
    }

    public String addStay(String name, String city, String address, String capStr, String ppnStr, String policyStr) {
        if (!app.isLoggedIn() || !app.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        if (name.length() < 3) return "stay name is too short.";
        if (stayRepo.getStayByName(name) != null) return "stay name already exists.";

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
        if (policyStr.equals("flexible")) policy = new FlexiblePolicy();
        else if (policyStr.equals("moderate")) policy = new ModeratePolicy();
        else if (policyStr.equals("strict")) policy = new StrictPolicy();
        else return "invalid cancellation policy.";

        Host host = (Host) app.getLoggedInUser();
        Stay stay = new Stay(name, city, address, capacity, ppn, policy, host.getUsername());
        host.addStay(stay);
        stayRepo.addStay(stay);

        return "stay \"" + name + "\" added successfully.";
    }

    public List<StayDTO> getCurrentHostStays() {
        if (!app.isLoggedIn() || !app.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Host host = (Host) app.getLoggedInUser();
        List<Stay> stays = host.getStays();
        if (stays.isEmpty()) return null;
        List<StayDTO> stayDTOs = new ArrayList<>();

        for (Stay stay : stays) {
            String status = stay.isActive() ? "active" : "inactive";
            String policyName = stay.getPolicy().getPolicyName();

            StayDTO dto = new StayDTO(
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

    public String deactivateStay(String name) {
        if (!app.isLoggedIn() || !app.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Stay stay = stayRepo.getStayByName(name);
        if (stay == null) return "stay not found.";
        if (!stay.isActive()) return "stay is already inactive.";

        stay.setActive(false); return "stay \"" + stay.getName() + "\" updated successfully.";
    }

    public String activateStay(String name) {
        if (!app.isLoggedIn() || !app.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Stay stay = stayRepo.getStayByName(name);
        if (stay == null) return "stay not found.";
        if (stay.isActive()) return "stay is already active.";

        stay.setActive(true); return "stay \"" + stay.getName() + "\" updated successfully.";
    }

    public List<BookingHostDTO> listBookingReqs() {
        if (!app.isLoggedIn() || !app.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Host host = (Host) app.getLoggedInUser();
        List<Stay> stays = host.getStays();
        List<BookingHostDTO> bookingDTOs = new ArrayList<>();

        if (stays.isEmpty()) return null;

        for (Stay stay : stays) {
            List<Booking> bookings = bookingRepo.getBookingsByStayName(stay.getName());
            for (Booking booking : bookings) {
                if (booking.getState() != BookingState.CANCELLED || booking.getState() != BookingState.REJECTED) {
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

    public String approveBooking(String bookingID) {
        if (!app.isLoggedIn() || !app.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Booking booking = bookingRepo.getBookingByID(bookingID);
        if (booking == null) return "booking not found.";
        if (booking.getState() != BookingState.REQUESTED) return "invalid booking status.";

        List<Booking> otherBookings = bookingRepo.getBookingsByStayName(booking.getStayName());
        for (Booking b : otherBookings) {
            if (b.getState() == BookingState.CONFIRMED &&
                    (booking.getFromDate().isBefore(b.getToDate()) || booking.getToDate().isAfter(b.getFromDate())))
                return "dates are not available.";
        }

        booking.setState(BookingState.CONFIRMED);
        /* Guest guest = guestRepo.getGuestByName(booking.getGuestUsername());
        guest.addBooking(booking); */
        return "booking " + bookingID + " confirmed successfully.";
    }

    public String rejectBooking(String bookingID) {
        if (!app.isLoggedIn() || !app.isHost() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");
        Booking booking = bookingRepo.getBookingByID(bookingID);
        if (booking == null) return "booking not found.";
        if (booking.getState() != BookingState.REQUESTED) return "invalid booking status.";

        booking.setState(BookingState.REJECTED);
        return "booking " + bookingID + " rejected successfully.";
    }
}
