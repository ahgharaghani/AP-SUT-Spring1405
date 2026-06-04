package view;

import controllers.ScoreboardController;
import models.commands.Command;
import models.commands.ScoreboardCommand;

public class ScoreboardView {
    public static void handleCommand(String query) {
        if (ScoreboardCommand.SHOW_SCOREBOARD.matches(query)) {
            Command.ParsedCommand parsed = ScoreboardCommand.SHOW_SCOREBOARD.parse(query);
            System.out.println(ScoreboardController.showScoreboard(parsed.getParam("sort")));
        } else if (ScoreboardCommand.BACK.matches(query)) {
            System.out.println(ScoreboardController.back());
        } else {
            System.out.println("invalid command");
        }
    }
}