package view;

import models.commands.MainMenuCommand;

public class MainView {
    public static void handleCommand(String query) {
        if (MainMenuCommand.QUIT.matches(query)) AppView.stop();
        else System.out.println("invalid command");
    }
}
