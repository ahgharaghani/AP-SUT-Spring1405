package view;

import controllers.GuestController;
import dto.BookingGuestDTO;
import models.commands.Command;
import models.commands.GuestMenuCommand;

import java.util.List;

public class GuestView {
    public static void handleCommand(String query) {
        if (GuestMenuCommand.SHOW_BALANCE.matches(query)) {
            String result = GuestController.getBalance();
            System.out.println(result);
        } else if (GuestMenuCommand.CHARGE_ACCOUNT.matches(query)) {
            String result = GuestController.chargeAccount(GuestMenuCommand.CHARGE_ACCOUNT.parse(query).getParam("amount"));
            System.out.println(result);;
        } else if (GuestMenuCommand.REQUEST_BOOKING.matches(query)) {
            Command.ParsedCommand parsedCommand = GuestMenuCommand.REQUEST_BOOKING.parse(query);
            String stay = parsedCommand.getParam("stay name");
            String fromDate = parsedCommand.getParam("from");
            String toDate = parsedCommand.getParam("to");
            String guests = parsedCommand.getParam("guests");
            String result = GuestController.requestBooking(stay, fromDate, toDate, guests);
            System.out.println(result);
        } else if (GuestMenuCommand.LIST_MY_BOOKINGS.matches(query)) {
            try {
                List<BookingGuestDTO> bookings = GuestController.listGuestBookings();
                if (bookings == null || bookings.isEmpty()) {
                    System.out.println("no bookings found."); return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("                        MY BOOKINGS                           \n");
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("\n");

                for (BookingGuestDTO booking : bookings) {
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("booking #").append(booking.num).append(" — ").append(booking.bookingId).append("\n");
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("stay    : ").append(booking.stayName).append("\n");
                    sb.append("status  : ").append(booking.status).append("\n");
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("\n");
                }

                System.out.print(sb);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        } else if (GuestMenuCommand.CANCEL_BOOKING.matches(query)) {
            Command.ParsedCommand parsedCommand = GuestMenuCommand.CANCEL_BOOKING.parse(query);
            String bookingID = parsedCommand.getParam("booking id");
            String result = GuestController.cancelBooking(bookingID);
            System.out.println(result);
        } else System.out.println("invalid command");
    }
}
