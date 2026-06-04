package view;

import controllers.ExploreController;
import dto.StaySearchDTO;
import dto.StayStatsDTO;
import models.commands.Command;
import models.commands.ExploreMenuCommand;

import java.util.List;

public class ExploreView {
    public static void handleCommand(String query) {
        if (ExploreMenuCommand.SEARCH_STAYS.matches(query)) {
            Command.ParsedCommand parsedCommand = ExploreMenuCommand.SEARCH_STAYS.parse(query);
            String city = parsedCommand.getParam("city");
            String guests = parsedCommand.getParam("guests");

            try {
                List<StaySearchDTO> stays = ExploreController.searchStays(city, guests);
                if (stays == null || stays.isEmpty()){
                    System.out.println("no stays found."); return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("SEARCH RESULTS\n");
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("\n");

                for (StaySearchDTO stay : stays) {
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("Stay #").append(stay.num).append(" — ").append(stay.name).append("\n");
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("Per Night: $").append(stay.ppn).append("\n");
                    sb.append("Host     : ").append(stay.hostBrand).append("\n");
                    sb.append("──────────────────────────────────────────────────────────────\n");
                    sb.append("\n");
                }

                System.out.print(sb);
            } catch (UnsupportedOperationException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else if (ExploreMenuCommand.SHOW_STAY.matches(query)) {
            Command.ParsedCommand parsedCommand = ExploreMenuCommand.SHOW_STAY.parse(query);
            String name = parsedCommand.getParam("stay name");

            try {
                StayStatsDTO stat = ExploreController.showStayStats(name);
                if (stat == null) {
                    System.out.println("stay not found."); return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("                         STAY DETAILS                         \n");
                sb.append("══════════════════════════════════════════════════════════════\n");
                sb.append("\n");
                sb.append("──────────────────────────────────────────────────────────────\n");
                sb.append(stat.name).append("\n");
                sb.append("──────────────────────────────────────────────────────────────\n");
                sb.append("city     : ").append(stat.city).append("\n");
                sb.append("address  : ").append(stat.address).append("\n");
                sb.append("capacity : ").append(stat.cap).append(" guests\n");
                sb.append("per Night: $").append(stat.ppn).append("\n");
                sb.append("policy   : ").append(stat.policy).append("\n");
                sb.append("host     : ").append(stat.brand).append("\n");
                sb.append("status   : ").append(stat.status).append("\n");
                sb.append("──────────────────────────────────────────────────────────────\n");

                System.out.println(sb);

            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        } else System.out.println("invalid command");
    }
}
