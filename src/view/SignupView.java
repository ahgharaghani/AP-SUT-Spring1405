package view;

import controllers.SignupController;
import models.commands.Command;
import models.commands.SignUpCommand;

public class SignupView {
    public static void handleCommand(String query) {
        if (SignUpCommand.SIGNUP.matches(query)) {
            Command.ParsedCommand parsedCommand = SignUpCommand.SIGNUP.parse(query);
            String username = parsedCommand.getParam("username");
            String password = parsedCommand.getParam("password");
            String result = SignupController.signUp(username, password);
            System.out.println(result);
        } else if (SignUpCommand.LOGIN.matches(query)) {
            Command.ParsedCommand parsedCommand = SignUpCommand.LOGIN.parse(query);
            String username = parsedCommand.getParam("username");
            String password = parsedCommand.getParam("password");
            String result = SignupController.login(username, password);
            System.out.println(result);
        } else System.out.println("invalid command");
    }
}
