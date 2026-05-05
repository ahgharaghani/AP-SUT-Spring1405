package view;

import controllers.AppController;
import models.commands.GlobalCommand;

import java.util.Scanner;

public class AppView {
    private static boolean running = true;

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (running && scanner.hasNextLine()) {
            String query = scanner.nextLine();
            if (GlobalCommand.CHANGE_MENU.matches(query)) {
                String result = AppController.changeMenu((GlobalCommand.CHANGE_MENU.parse(query).getParam("menu")));
                System.out.println(result);
                continue;
            } else if (GlobalCommand.SHOW_CURRENT_MENU.matches(query)) {
                String result = AppController.getCurrentMenu();
                System.out.println("current menu: " + result);
                continue;
            } else if (GlobalCommand.TIME_SET.matches(query)) {
                String date = GlobalCommand.TIME_SET.parse(query).getParam("date");
                String result = AppController.setSystemDate(date);
                System.out.println(result);
                continue;
            }

            /* I didn't call App.getMenu here because it won't make sense for view layer to directly call a method from model layer. */
            /* Also it won't make sense for the controller layer to return an object from model layer. */
            String currentMenu = AppController.getCurrentMenu();
            if (currentMenu.equals("main menu")) MainView.handleCommand(query);
            else if (currentMenu.equals("auth menu")) AuthView.handleCommand(query);
            else if (currentMenu.equals("host menu")) HostView.handleCommand(query);
            else if (currentMenu.equals("guest menu")) GuestView.handleCommand(query);
            else if (currentMenu.equals("explore menu")) ExploreView.handleCommand(query);
        }

        scanner.close();
    }

    public static void stop() {
        running = false;
    }
}
