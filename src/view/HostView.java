package view;

import controllers.HostController;
import dto.BookingHostDTO;
import dto.StayStatsDTO;
import models.commands.Command;
import models.commands.HostMenuCommand;

import java.util.List;

public class HostView {
    public static void handleCommand(String query) {
        if (HostMenuCommand.SHOW_BALANCE.matches(query)) {
            String result = HostController.getBalance();
            System.out.println(result);
        } else if (HostMenuCommand.ADD_STAY.matches(query)) {
            Command.ParsedCommand parsedCommand = HostMenuCommand.ADD_STAY.parse(query);
            String name = parsedCommand.getParam("stay name");
            String city = parsedCommand.getParam("city");
            String address = parsedCommand.getParam("address");
            String capacity = parsedCommand.getParam("capacity");
            String ppn = parsedCommand.getParam("price per night");
            String policy = parsedCommand.getParam("policy");
            String result = HostController.addStay(name, city, address, capacity, ppn, policy);
            System.out.println(result);
        } else if (HostMenuCommand.LIST_MY_STAYS.matches(query)) {
            try {
                List<StayStatsDTO> stats = HostController.getCurrentHostStays();
                if (stats == null || stats.isEmpty()) {
                    System.out.println("no stays found."); return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("                           MY STAYS                           \n");
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("\n");

                for (StayStatsDTO stat : stats) {
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("stay #").append(stat.id).append(" — ").append(stat.name).append("\n");
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("city     : ").append(stat.city).append("\n");
                    sb.append("address  : ").append(stat.address).append("\n");
                    sb.append("capacity : ").append(stat.cap).append("\n");
                    sb.append("per Night: $").append(stat.ppn).append("\n");
                    sb.append("policy   : ").append(stat.policy).append("\n");
                    sb.append("status   : ").append(stat.status).append("\n");
                    sb.append("──────────────────────────────────────────────────────────────\n\n");
                }

                System.out.print(sb);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        } else if (HostMenuCommand.DEACTIVATE_STAY.matches(query)) {
            Command.ParsedCommand parsedCommand = HostMenuCommand.DEACTIVATE_STAY.parse(query);
            String name = parsedCommand.getParam("stay name");
            String result = HostController.deactivateStay(name);
            System.out.println(result);
        } else if (HostMenuCommand.ACTIVATE_STAY.matches(query)) {
            Command.ParsedCommand parsedCommand = HostMenuCommand.ACTIVATE_STAY.parse(query);
            String name = parsedCommand.getParam("stay name");
            String result = HostController.activateStay(name);
            System.out.println(result);
        } else if (HostMenuCommand.LIST_BOOKING_REQUESTS.matches(query)) {
            try {
                List<BookingHostDTO> reqs = HostController.listBookingReqs();
                if (reqs == null || reqs.isEmpty()) {
                    System.out.println("no booking requests found."); return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("                     BOOKING REQUESTS                         \n");
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("\n");

                for (BookingHostDTO req : reqs) {
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("request #").append(req.number).append(" — ").append(req.bookingID).append("\n");
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("stay    : ").append(req.stayName).append("\n");
                    sb.append("guest   : ").append(req.guestName).append("\n");
                    sb.append("from    : ").append(req.fromDate).append("\n");
                    sb.append("to      : ").append(req.toDate).append("\n");
                    sb.append("guests  : ").append(req.guestsNum).append("\n");
                    sb.append("──────────────────────────────────────────────────────────────\n\n");
                }

                System.out.print(sb);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        } else if (HostMenuCommand.CONFIRM_BOOKING.matches(query)) {
            Command.ParsedCommand parsedCommand = HostMenuCommand.CONFIRM_BOOKING.parse(query);
            String bookingID = parsedCommand.getParam("booking id");
            String result = HostController.approveBooking(bookingID);
            System.out.println(result);
        } else if (HostMenuCommand.REJECT_BOOKING.matches(query)) {
            Command.ParsedCommand parsedCommand = HostMenuCommand.REJECT_BOOKING.parse(query);
            String bookingID = parsedCommand.getParam("booking id");
            String result = HostController.rejectBooking(bookingID);
            System.out.println(result);
        } else System.out.println("invalid command");
    }
}