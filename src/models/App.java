package models;

import models.users.User;

import java.time.LocalDate;

public class App {
    private static App instance;
    private LocalDate currentDate;
    private Menu currentMenu;
    private User loggedInUser;

    private App() {
        this.currentMenu = Menu.MAIN;
        this.loggedInUser = null;
        this.currentDate = LocalDate.now();
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu menu) {
        this.currentMenu = menu;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public void setCurrentDate(LocalDate newDate) {
        this.currentDate = newDate;
    }

    public LocalDate getDate() {
        return currentDate;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public boolean isGuest() {
        return loggedInUser != null && loggedInUser.getRole().equals("guest");
    }

    public boolean isHost() {
        return loggedInUser != null && loggedInUser.getRole().equals("host");
    }
}