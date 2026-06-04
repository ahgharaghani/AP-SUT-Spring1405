package controllers;

import models.App;
import models.Repository;
import models.User;
import models.enums.Menu;

import java.util.List;
import java.util.Locale;

public class ScoreboardController {
    public static String showScoreboard(String sortType) {
        String normalized = sortType.toLowerCase();
        List<User> users = Repository.getAllPlayers();

        if (normalized.equalsIgnoreCase("games_played")) {
                users.sort((u1, u2) -> Integer.compare(u2.getGamesPlayed(), u1.getGamesPlayed()));
                return buildGamesPlayed(users);
        } else if (normalized.equalsIgnoreCase("point")) {
                users.sort((u1, u2) -> Double.compare(u2.getPoints(), u1.getPoints()));
                return buildPoints(users);
        } else if (normalized.equalsIgnoreCase("games_won")) {
                users.sort((u1, u2) -> Integer.compare(u2.getGamesWon(), u1.getGamesWon()));
                return buildGamesWon(users);
        } else return "invalid sort type";

    }

    public static String back() {
        App.setCurrentMenu(Menu.MAIN);
        return "you're now in main menu";
    }

    private static String buildGamesPlayed(List<User> users) {
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(user.getUsername())
                    .append("-> games played: ")
                    .append(user.getGamesPlayed())
                    .append("\n");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static String buildPoints(List<User> users) {
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(user.getUsername())
                    .append("-> points: ")
                    .append(formatPoint(user.getPoints()))
                    .append("\n");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static String buildGamesWon(List<User> users) {
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(user.getUsername())
                    .append("-> games won: ")
                    .append(user.getGamesWon())
                    .append("\n");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static String formatPoint(double value) {
        return String.valueOf((long) Math.floor(value));
    }
}