package models;

import models.enums.Menu;
import models.users.User;

import java.time.LocalDate;

public class App {
    private static LocalDate currentDate = LocalDate.now();
    private static Menu currentMenu = Menu.MAIN;
    private static User loggedInUser = null;

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu menu) {
        currentMenu = menu;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static void setCurrentDate(LocalDate newDate) {
        currentDate = newDate;
    }

    public static LocalDate getDate() {
        return currentDate;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public static boolean isGuest() {
        return loggedInUser != null && loggedInUser.getRole().equals("guest");
    }

    public static boolean isHost() {
        return loggedInUser != null && loggedInUser.getRole().equals("host");
    }
}