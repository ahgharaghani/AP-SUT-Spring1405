package view;

import controller.GameController;
import controller.MainController;
import model.command.MainCommand;

import java.util.Scanner;

public class MainView {
    public static void handleCommand(Scanner scanner) {
        String query = scanner.nextLine();

        if (MainCommand.ADD.matches(query)) {
            String name = MainCommand.ADD.getParameter("name");
            String type = MainCommand.ADD.getParameter("type");
            String role = MainCommand.ADD.getParameter("role");
            String result = MainController.addAnimalToFarm(name, type, role);
            System.out.println(result);
        } else if (MainCommand.START_GAME.matches(query)) {
            String result = MainController.startGame();
            System.out.println(result);
        } else if (MainCommand.EXIT.matches(query)) {
            AppView.stop();
        } else System.out.println("Invalid command");
    }
}
