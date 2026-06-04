package view;

import controllers.GameController;
import dto.GameResultDTO;
import models.commands.Command;
import models.commands.GameCommand;

public class GameView {
    public static void handleCommand(String query) {
        if (GameCommand.SHOW_TURN.matches(query)) {
            System.out.println(GameController.showTurn());
        } else if (GameCommand.SKIP_TURN.matches(query)) {
            System.out.println(GameController.skipTurn());
        } else if (GameCommand.SHOW_DETAILS.matches(query)) {
            System.out.println(GameController.showDetails());
        } else if (GameCommand.SHOW_STATS.matches(query)) {
            Command.ParsedCommand parsed = GameCommand.SHOW_STATS.parse(query);
            System.out.println(GameController.showStats(
                    parsed.getParam("knight"),
                    parsed.getParam("username")
            ));
        } else if (GameCommand.ATTACK.matches(query)) {
            Command.ParsedCommand parsed = GameCommand.ATTACK.parse(query);
            Object result = GameController.attack(parsed.getParam("knight"));
                System.out.println(formatGameResult(result));
                System.out.println(result);

        } else if (GameCommand.SKILL.matches(query)) {
            Command.ParsedCommand parsed = GameCommand.SKILL.parse(query);
            Object result = GameController.skill(
                    parsed.getParam("skill"),
                    parsed.getParam("knight")
            );
                System.out.println(formatGameResult(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private static String formatGameResult(GameResultDTO result) {
        StringBuilder sb = new StringBuilder();
        sb.append("war has ended\n");
        sb.append("--------------------\n");
        sb.append("winner: ").append(result.getWinnerUsername())
                .append(" - points: ").append(result.getWinnerPoints()).append("\n");

        for (GameResultDTO.KnightResultDTO knight : result.getWinnerKnights()) {
            sb.append(formatKnightResult(knight)).append("\n");
        }

        sb.append("--------------------\n");
        sb.append("loser: ").append(result.getLoserUsername())
                .append(" - points: ").append(result.getLoserPoints()).append("\n");

        for (GameResultDTO.KnightResultDTO knight : result.getLoserKnights()) {
            sb.append(formatKnightResult(knight)).append("\n");
        }

        return sb.toString().trim();
    }

    private static String formatKnightResult(GameResultDTO.KnightResultDTO knight) {
        return knight.getOwnerUsername() + "'s " + knight.getKnightName() +
                ": damage dealt: " + knight.getDamageDealt() +
                " - HP remained: " + knight.getHpRemained() +
                " - " + (knight.isAlive() ? "alive" : "dead") +
                " - point: " + knight.getPoints();
    }

}