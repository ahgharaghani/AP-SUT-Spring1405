package controllers;

import dto.BookingGuestDTO;
import models.*;
import models.users.Guest;
import repositories.BookingRepository;
import repositories.GuestRepository;
import repositories.HostRepository;
import repositories.StayRepository;
import utils.BookingIDGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GuestController {
    private static GuestController instance;
    private HostRepository hostRepo;
    private GuestRepository guestRepo;
    private StayRepository stayRepo;
    private BookingRepository bookingRepo;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private final App app;

    private GuestController() {
        this.app = App.getInstance();
        this.hostRepo = HostRepository.getInstance();
        this.guestRepo = GuestRepository.getInstance();
        this.stayRepo = StayRepository.getInstance();
        this.bookingRepo = BookingRepository.getInstance();
    }

    public static GuestController getInstance() {
        if (instance == null) instance = new GuestController();
        return instance;
    }

    private boolean validateCurrentMenu() {
        return app.getCurrentMenu() == Menu.GUEST;
    }

    public String getBalance() {
        if (!app.isLoggedIn() || !app.isGuest() || !validateCurrentMenu())throw new UnsupportedOperationException("invalid command");;

        Guest guest = (Guest) app.getLoggedInUser();
        return "your balance: $" + guest.getBalance();
    }

    public String chargeAccount(String amountStr) {
        if (!app.isLoggedIn() || !app.isGuest() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");;

        int amount;
        try {
            amount = Integer.parseInt(amountStr);
            if (amount <= 0) {
                return "Amount must be greater than 0.";
            }
        } catch (NumberFormatException e) {
            return "Amount must be greater than 0.";
        }

        Guest currentUser = (Guest) app.getLoggedInUser();
        currentUser.addBalance(amount);
        return "Account charged successfully. Current balance: $" + currentUser.getBalance();
    }

    public String requestBooking(String stayName, String fromDate, String toDate, String numGuestsStr) {
        if (!app.isLoggedIn() || !app.isGuest() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Guest guest = (Guest) app.getLoggedInUser();

        Stay stay = stayRepo.getStayByName(stayName);
        if (stay == null) return "stay not found.";
        if (!stay.isActive()) return "stay is not available.";

        LocalDate parsedFromDate = LocalDate.parse(fromDate, FORMATTER);
        LocalDate parsedToDate = LocalDate.parse(toDate, FORMATTER);
        if (parsedFromDate.isAfter(parsedToDate)) return "invalid date range.";

        int numGuests;
        try {
            numGuests = Integer.parseInt(numGuestsStr);
            if (numGuests <= 0) {
                return "invalid guests count.";
            }
        } catch (NumberFormatException e) {
            return "invalid guests count.";
        }

        if (numGuests > stay.getCapacity()) return "not enough capacity.";

        long totalPrice = ChronoUnit.DAYS.between(parsedFromDate, parsedToDate) * stay.getPricePerNight();

        if (guest.getBalance() < totalPrice) return "not enough balance.";

        guest.deductBalance((int) totalPrice);
        String bookingID = BookingIDGenerator.generateId(app.getDate());
        Booking booking = new Booking(bookingID, guest.getUsername(), stayName, parsedFromDate, parsedToDate, numGuests, (int) totalPrice);

        return "booking request created successfully. booking id: " + bookingID;
    }

    public List<BookingGuestDTO> listGuestBookings() {
        if (!app.isLoggedIn() || !app.isGuest() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Guest guest = (Guest) app.getLoggedInUser();
        List<Booking> bookings = guest.getBookings();
        List<BookingGuestDTO> bookingDTOs = new ArrayList<>();

        if (bookings.isEmpty()) return null;

        for (Booking booking : bookings) {
            BookingGuestDTO dto = new BookingGuestDTO(
                    bookingDTOs.size() + 1,
                    booking.getId(),
                    booking.getStayName(),
                    booking.getState().getDisplayName()
            );
            bookingDTOs.add(dto);
        }

        return bookingDTOs;
    }

    public String cancelBooking(String bookingID) {
        if (!app.isLoggedIn() || !app.isGuest() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Guest guest = (Guest) app.getLoggedInUser();
        Booking booking = bookingRepo.getBookingByID(bookingID);
        if (booking == null) return "booking not found.";

        if (booking.getState() != BookingState.CONFIRMED) return "cannot cancel a booking that is not confirmed.";

        if (app.getDate().isAfter(booking.getToDate())) return "cannot cancel a booking that has already passed.";

        Stay stay = stayRepo.getStayByName(booking.getStayName());
        int refund = stay.getPolicy().calculateRefund(booking.getTotalPrice(), app.getDate(), booking.getFromDate());
        booking.setState(BookingState.CANCELLED);
        guest.addBalance(refund);

        return "booking " +bookingID + " cancelled successfully. refund: $" + refund;
    }
}
