package controllers;

import models.App;
import models.User;
import models.Repository;
import models.enums.Menu;

import java.util.regex.Pattern;

public class SignupController {
    private static final String USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9_]*$";
    private static final String PASSWORD_PATTERN = "^(?=.*[%@#$^&!])[a-zA-Z]\\S*$";

    public static String signUp(String username, String password) {
        if (!Pattern.matches(USERNAME_PATTERN, username)) return "invalid username";
        if (Repository.getPlayerByUsername(username) != null) return "username already exists";
        if (!Pattern.matches(PASSWORD_PATTERN, password)) return "invalid password";

        User newUser = new User(username, password);
        Repository.addPlayer(newUser);
        return "registered Successfully";
    }

    public static String login(String username, String password) {
        User userToBeFound = Repository.getPlayerByUsername(username);
        if (userToBeFound == null) return "username not found";
        if (!userToBeFound.getPassword().equals(password)) return "password incorrect";

        App.setCurrentPlayer(userToBeFound);
        App.setCurrentMenu(Menu.MAIN);

        return "logged in successfully";
    }
}