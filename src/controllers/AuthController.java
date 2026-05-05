package controllers;

import models.App;
import models.enums.Menu;
import models.Repository;
import models.users.Guest;
import models.users.Host;

import java.util.regex.Pattern;

public class AuthController {
    private static final String USERNAME_PATTERN = "^[a-z0-9]{3,}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$";
    private static final String EMAIL_PATTERN = "^(?!\\.)(?!.*\\.$)(?!.*\\.\\.)[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)?@[a-z]+\\.com$";

    private static boolean validateCurrentMenu() {
        return App.getCurrentMenu() == Menu.AUTH;
    }

    public static String registerGuest(String username, String password, String email) {
        if (!validateCurrentMenu()) return "invalid command";
        if (username.length() < 3) return "username is too short.";
        if (!Pattern.matches(USERNAME_PATTERN, username)) return "incorrect username format.";
        if (Repository.getGuestByName(username) != null || Repository.getHostByName(username) != null)
            return "username already exists.";
        if (!Pattern.matches(PASSWORD_PATTERN, password)) return  "incorrect password format.";
        if (!Pattern.matches(EMAIL_PATTERN, email)) return "incorrect email format.";
        if (Repository.getGuestByEmail(email) != null || Repository.getHostByEmail(email) != null)
            return "email already exists.";

        Guest newGuest = new Guest(username, password, email);
        Repository.addGuest(newGuest);
        return "guest account created successfully.";
    }

    public static String registerHost(String username, String brandName, String password, String email) {
        if (!validateCurrentMenu()) return "invalid command";
        if (username.length() < 3) return "username is too short.";
        if (!Pattern.matches(USERNAME_PATTERN, username)) return "incorrect username format.";
        if (Repository.getGuestByName(username) != null || Repository.getHostByName(username) != null)
            return "username already exists.";
        if (brandName.length() < 3) return "brand name is too short.";
        if (!Pattern.matches(PASSWORD_PATTERN, password)) return  "incorrect password format.";
        if (!Pattern.matches(EMAIL_PATTERN, email)) return "incorrect email format.";
        if (Repository.getGuestByEmail(email) != null || Repository.getHostByEmail(email) != null)
            return "email already exists.";

        Host newHost = new Host(username, password, email, brandName);
        Repository.addHost(newHost);
        return "host account created successfully.";
    }

    public static String login(String username, String password, String userType) {
        if (!validateCurrentMenu()) return "invalid command";
        if (userType.equals("guest")) {
            Guest guest = Repository.getGuestByName(username);
            if (guest == null) return "no guest account found with the provided username.";
            if (!guest.getPassword().equals(password)) return "password is incorrect.";

            App.setLoggedInUser(guest);
        } else {
            Host host = Repository.getHostByName(username);
            if (host == null) return "no host account found with the provided username.";
            if (!host.getPassword().equals(password)) return "password is incorrect.";

            App.setLoggedInUser(host);
        }
        App.setCurrentMenu(Menu.MAIN);
        return "logged in successfully. changed menu to: main menu";
    }

    public static String logout() {
        if (!validateCurrentMenu()) return "invalid command";
        if (!App.isLoggedIn()) return "you are not logged in.";

        App.setLoggedInUser(null);
        return "logged out successfully.";
    }

    public static String restorePassword(String username, String email, String userType) {
        if (!validateCurrentMenu()) return "invalid command";
        if (!Pattern.matches(EMAIL_PATTERN, email)) return "incorrect email format.";
        if (userType.equals("guest")) {
            Guest guest = Repository.getGuestByName(username);
            if (guest == null) return "no guest account found with the provided username.";
            if (!guest.getEmail().equals(email)) return "email doesn't match the registered email for this username.";

            return "your password is: " + guest.getPassword();
        } else {
            Host host = Repository.getHostByName(username);
            if (host == null) return "no host account found with the provided username.";
            if (!host.getEmail().equals(email)) return "email doesn't match the registered email for this username.";

            return "your password is: " + host.getPassword();
        }
    }
}
