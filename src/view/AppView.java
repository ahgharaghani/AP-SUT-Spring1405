package view;

import controller.AppController;
import controller.GameController;

import java.util.Scanner;

public class AppView {
    private static boolean running = true;
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {

        while (running) {
            String currentMenu = AppController.getCurrentMenu();

            if (currentMenu.equals("main")) {
                MainView.handleCommand(scanner);
            } else if (currentMenu.equals("game")) {
                GameView.handleCommand(scanner);
            }
        }

        scanner.close();
    }

    public static void stop() { running = false; }
}
