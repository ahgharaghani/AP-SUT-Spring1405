package view;

import controllers.MainController;
import dto.KnightDTO;
import models.commands.Command;
import models.commands.MainCommand;

import java.util.List;

public class MainView {
    public static void handleCommand(String query) {
        if (MainCommand.LOGOUT.matches(query)) {
            System.out.println(MainController.logout());
        } else if (MainCommand.SHOW_KNIGHT_DETAILS.matches(query)) {
            List<KnightDTO> dtos = MainController.showKnightDetails();

            StringBuilder sb = new StringBuilder();
            sb.append("characters:\n");
            for (KnightDTO dto : dtos) {
                sb.append("--------------------\n");
                sb.append("name: ").append(dto.getName()).append(" - class: ").append(dto.getClassType()).append("\n");
                sb.append("HP: ").append(dto.getHp())
                        .append(" - attack: ").append(dto.getAttack())
                        .append(" - magic attack: ").append(dto.getMagic())
                        .append(" - defense: ").append(dto.getDefense())
                        .append(" - speed: ").append(dto.getSpeed()).append("\n");
                sb.append("skills: ");
                for (int i = 0; i < dto.getSkills().size(); i++) {
                    sb.append(dto.getSkills().get(i));
                    if (i != dto.getSkills().size() - 1) sb.append(" - ");
                    else sb.append("\n");
                }
            }
            sb.append("--------------------");
            System.out.println(sb);
        } else if (MainCommand.SCOREBOARD.matches(query)) {
            System.out.println(MainController.gotoScoreBoard());
        } else if (MainCommand.PLAY_AGAINST.matches(query)) {
            Command.ParsedCommand parsedCommand = MainCommand.PLAY_AGAINST.parse(query);
            String username = parsedCommand.getParam("username");
            String result = MainController.playAgainst(username);
            System.out.println(result);

            if (!result.startsWith("you're playing with ")) return;

            while (MainController.whoMustChoose() != null) {
                System.out.println("choosing knight for " + MainController.whoMustChoose() + ":");
                String knightName = AppView.getScanner().nextLine().trim();
                System.out.println(MainController.chooseKnight(knightName, MainController.whoMustChoose()));
            }

            System.out.println("mobarake kheylia");
        } else {
            System.out.println("invalid command");
        }
    }
}