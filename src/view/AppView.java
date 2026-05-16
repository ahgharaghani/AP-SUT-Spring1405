package view;

import controllers.AppController;
import models.commands.GlobalCommand;

import java.util.Scanner;

public class AppView {
    private static boolean running = true;
    private static Scanner scanner = new Scanner(System.in);

    public static void run() {
        while (running && scanner.hasNextLine()) {
            String query = scanner.nextLine();
            if (GlobalCommand.SHOW_CURRENT_MENU.matches(query)) {
                String result = AppController.getCurrentMenu();
                System.out.println("current menu: " + result);
                continue;
            } else if (GlobalCommand.EXIT.matches(query)) {
                break;
            }

            String currentMenu = AppController.getCurrentMenu();
            if (currentMenu.equals("main menu")) MainView.handleCommand(query);
            else if (currentMenu.equals("signup menu")) SignupView.handleCommand(query);
        }

        scanner.close();
    }

    public static void stop() {
        running = false;
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
