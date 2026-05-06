package controllers;

import models.*;
import models.enums.BookingState;
import models.enums.Menu;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppController {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static String changeMenu(String menuName) {
        Menu menu = Menu.fromString(menuName);
        if (menu == null) return "404 page not found.";
        if (menu == Menu.HOST && !App.isHost()) return "you need to login as a host before accessing the host menu.";
        if (menu == Menu.GUEST && !App.isGuest()) return "you need to login as a guest before accessing the guest menu.";
        App.setCurrentMenu(menu);
        return "changed menu to: " + menu.getDisplayName() + ".";
    }

    public static String getCurrentMenu() {
        return App.getCurrentMenu().getDisplayName();
    }

    public static String setSystemDate(String dateString) {
        try {
            ThirtyDayDate newDate = ThirtyDayDate.parse(dateString, FORMATTER);

            if (newDate == null) {
                return "invalid date format.";
            }

            if (App.getDate() != null && newDate.isBefore(App.getDate())) {
                return "cannot set date to the past.";
            }

            App.setCurrentDate(newDate);
            List<Booking> bookings = Repository.getAllBookings();
            for (Booking booking : bookings) {
                if (booking.getState() == BookingState.CONFIRMED && !App.getDate().isBefore(booking.getToDate()))
                    booking.setState(BookingState.COMPLETED);
            }
            return "current date is now " + dateString + ".";
        } catch (Exception e) {
            return "invalid date format.";
        }
    }
}
