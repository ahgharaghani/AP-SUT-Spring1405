package controllers;

import dto.BookingGuestDTO;
import models.*;
import models.enums.BookingState;
import models.enums.Menu;
import models.users.Guest;
import models.users.Host;
import utils.BookingIDGenerator;
import utils.DateUtils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GuestController {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static boolean validateCurrentMenu() {
        return App.getCurrentMenu() == Menu.GUEST;
    }

    public static String getBalance() {
        if (!App.isLoggedIn() || !App.isGuest() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");;

        Guest guest = (Guest) App.getLoggedInUser();
        return "your balance: $" + guest.getBalance();
    }

    public static String chargeAccount(String amountStr) {
        if (!App.isLoggedIn() || !App.isGuest() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");;

        int amount;
        try {
            amount = Integer.parseInt(amountStr);
            if (amount <= 0) {
                return "Amount must be greater than 0.";
            }
        } catch (NumberFormatException e) {
            return "Amount must be greater than 0.";
        }

        Guest currentUser = (Guest) App.getLoggedInUser();
        currentUser.addBalance(amount);
        return "Account charged successfully. Current balance: $" + currentUser.getBalance();
    }

    public static String requestBooking(String stayName, String fromDate, String toDate, String numGuestsStr) {
        if (!App.isLoggedIn() || !App.isGuest() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Guest guest = (Guest) App.getLoggedInUser();

        Stay stay = Repository.getStayByName(stayName);
        if (stay == null) return "stay not found.";
        if (!stay.isActive()) return "stay is not available.";

        ThirtyDayDate parsedFromDate, parsedToDate;
        try {
            parsedFromDate = ThirtyDayDate.parse(fromDate, FORMATTER);
            parsedToDate = ThirtyDayDate.parse(toDate, FORMATTER);
            if (parsedFromDate == null || parsedToDate == null) return "invalid date format.";
        } catch (DateTimeParseException e) {
            return "invalid date format.";
        }
        if (!parsedFromDate.isBefore(parsedToDate)) return "invalid date range.";

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

        long totalPrice = DateUtils.daysUntil(parsedFromDate, parsedToDate) * stay.getPricePerNight();

        if (guest.getBalance() < totalPrice) return "not enough balance.";

        guest.deductBalance((int) totalPrice);
        String bookingID = BookingIDGenerator.generateId(App.getDate());
        Booking booking = new Booking(bookingID, guest.getUsername(), stayName, parsedFromDate, parsedToDate, numGuests, (int) totalPrice);
        Repository.addBooking(booking);
        guest.addBooking(booking);
        return "booking request created successfully. booking id: " + bookingID;
    }

    public static List<BookingGuestDTO> listGuestBookings() {
        if (!App.isLoggedIn() || !App.isGuest() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Guest guest = (Guest) App.getLoggedInUser();
        List<Booking> bookings = guest.getBookings();
        List<BookingGuestDTO> bookingDTOs = new ArrayList<>();

        if (bookings.isEmpty()) return null;

        for (Booking booking : bookings) {
            BookingGuestDTO dto = new BookingGuestDTO(
                    0,
                    booking.getId(),
                    booking.getStayName(),
                    booking.getState().getDisplayName()
            );
            bookingDTOs.add(dto);
        }

        bookingDTOs.sort(
                Comparator.<BookingGuestDTO>comparingInt(
                        dto -> Integer.parseInt(dto.bookingId.split("-")[2])
                )
        );

        for (int i = 0; i < bookingDTOs.size(); i++) {
            bookingDTOs.get(i).num = i + 1;
        }

        return bookingDTOs;
    }

    public static String cancelBooking(String bookingID) {
        if (!App.isLoggedIn() || !App.isGuest() || !validateCurrentMenu()) throw new UnsupportedOperationException("invalid command");

        Guest guest = (Guest) App.getLoggedInUser();
        Booking booking = Repository.getBookingByID(bookingID);
        if (booking == null) return "booking not found.";

        if (booking.getState() != BookingState.CONFIRMED) return "cannot cancel a booking that is not confirmed.";

        if (App.getDate().isAfter(booking.getToDate())) return "cannot cancel a booking that has already passed.";

        Stay stay = Repository.getStayByName(booking.getStayName());
        int refund = stay.getPolicy().calculateRefund(booking.getTotalPrice(), App.getDate(), booking.getFromDate());
        booking.setState(BookingState.CANCELLED);
        guest.addBalance(refund);
        Host host = Repository.getHostByName(stay.getHostUsername());
        host.deductBalance(refund);

        return "booking " +bookingID + " cancelled successfully. refund: $" + refund;
    }
}
