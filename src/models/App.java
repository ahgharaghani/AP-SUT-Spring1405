package models;

import models.enums.Menu;

public class App {
    private static Menu currentMenu = Menu.SIGNUP;
    private static User currentUser = null;
    private static GameSession currentGameSession = null;

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu menu) {
        currentMenu = menu;
    }

    public static User getCurrentPlayer() {
        return currentUser;
    }

    public static void setCurrentPlayer(User currentUser) {
        App.currentUser = currentUser;
    }

    public static GameSession getCurrentGameSession() {
        return currentGameSession;
    }

    public static void setCurrentGameSession(GameSession currentGameSession) {
        App.currentGameSession = currentGameSession;
    }
}
