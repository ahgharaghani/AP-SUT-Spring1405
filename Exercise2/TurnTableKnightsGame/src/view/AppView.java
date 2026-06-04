package view;

import controllers.AppController;
import models.commands.GlobalCommand;

import java.util.Scanner;

public class AppView {
    private static boolean running = true;
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        while (running && scanner.hasNextLine()) {
            String query = scanner.nextLine();

            if (GlobalCommand.SHOW_CURRENT_MENU.matches(query)) {
                System.out.println("current menu: " + AppController.getCurrentMenu());
                continue;
            } else if (GlobalCommand.EXIT.matches(query)) {
                break;
            }

            String currentMenu = AppController.getCurrentMenu();
            if (currentMenu.equals("signup menu")) {
                SignupView.handleCommand(query);
            } else if (currentMenu.equals("main menu")) {
                MainView.handleCommand(query);
            } else if (currentMenu.equals("game menu")) {
                GameView.handleCommand(query);
            } else if (currentMenu.equals("scoreboard menu")) {
                ScoreboardView.handleCommand(query);
            }
        }

        scanner.close();
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void stop() {
        running = false;
    }
}