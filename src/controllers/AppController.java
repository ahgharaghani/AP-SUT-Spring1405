package controllers;

import models.App;
import models.Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppController {
    private static AppController instance;

//    private static AuthController authController;
//    private static ExploreController exploreController;
//    private static GuestController guestController;
//    private static HostController hostController;
//    private static MainController mainController;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static App app;

    private AppController() {
        app = App.getInstance();
    }

    public static AppController getInstance() {
        if (instance == null) instance = new AppController();
        return instance;
    }

    public String changeMenu(String menuName) {
        Menu menu = Menu.fromString(menuName);
        if (menu == null) return "404 page not found.";
        if (menu == Menu.HOST && app.isHost()) return "you need to login as a host before accessing the host menu.";
        if (menu == Menu.GUEST && app.isGuest()) return "you need to login as a guest before accessing the guest menu.";
        app.setCurrentMenu(menu);
        return "changed menu to: " + menu.getDisplayName();
    }

    public String showCurrentMenu() {
        return "current menu: " + app.getCurrentMenu().getDisplayName();
    }

    public String setSystemDate(String dateString) {
        try {
            LocalDate newDate = LocalDate.parse(dateString, FORMATTER);

            if (newDate.getDayOfMonth() > 30) {
                return "invalid date format.";
            }

            if (newDate.isBefore(app.getDate())) {
                return "cannot set date to the past.";
            }

            app.setCurrentDate(newDate);
            return "current date is now " + dateString;
        } catch (Exception e) {
            return "invalid date format.";
        }
    }
}
