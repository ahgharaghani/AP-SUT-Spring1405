package view;

import controllers.AuthController;
import models.commands.AuthMenuCommand;
import models.commands.Command;

public class AuthView {
    public static void handleCommand(String query) {
        if (AuthMenuCommand.CREATE_GUEST.matches(query)) {
            Command.ParsedCommand parsedCommand = AuthMenuCommand.CREATE_GUEST.parse(query);
            String user = parsedCommand.getParam("username");
            String password = parsedCommand.getParam("password");
            String email = parsedCommand.getParam("email");
            String result = AuthController.registerGuest(user, password, email);
            System.out.println(result);
        } else if (AuthMenuCommand.CREATE_HOST.matches(query)) {
            Command.ParsedCommand parsedCommand = AuthMenuCommand.CREATE_HOST.parse(query);
            String user = parsedCommand.getParam("username");
            String brand = parsedCommand.getParam("brand");
            String password = parsedCommand.getParam("password");
            String email = parsedCommand.getParam("email");
            String result = AuthController.registerHost(user, brand, password, email);
            System.out.println(result);
        } else if (AuthMenuCommand.LOGIN_GUEST.matches(query)) {
            Command.ParsedCommand parsedCommand = AuthMenuCommand.LOGIN_GUEST.parse(query);
            String user = parsedCommand.getParam("username");
            String password = parsedCommand.getParam("password");
            String result = AuthController.login(user, password, "guest");
            System.out.println(result);
        } else if (AuthMenuCommand.LOGIN_HOST.matches(query)) {
            Command.ParsedCommand parsedCommand = AuthMenuCommand.LOGIN_HOST.parse(query);
            String user = parsedCommand.getParam("username");
            String password = parsedCommand.getParam("password");
            String result = AuthController.login(user, password, "host");
            System.out.println(result);
        } else if (AuthMenuCommand.LOGOUT.matches(query)) {
            String result = AuthController.logout();
            System.out.println(result);
        } else if (AuthMenuCommand.FORGOT_PASSWORD_GUEST.matches(query)) {
            Command.ParsedCommand parsedCommand = AuthMenuCommand.FORGOT_PASSWORD_GUEST.parse(query);
            String user = parsedCommand.getParam("username");
            String email = parsedCommand.getParam("email");
            String result = AuthController.restorePassword(user, email, "guest");
            System.out.println(result);
        } else if (AuthMenuCommand.FORGOT_PASSWORD_HOST.matches(query)) {
            Command.ParsedCommand parsedCommand = AuthMenuCommand.FORGOT_PASSWORD_HOST.parse(query);
            String user = parsedCommand.getParam("username");
            String email = parsedCommand.getParam("email");
            String result = AuthController.restorePassword(user, email, "host");
            System.out.println(result);
        } else System.out.println("invalid command");
    }
}
