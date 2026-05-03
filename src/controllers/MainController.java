package controllers;

import models.App;
import models.Menu;

public class MainController {
    private static MainController instance;
    private final App app;

    private MainController() {
        this.app = App.getInstance();
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public String quitApp() {
        if (app.getCurrentMenu() != Menu.MAIN) return "invalid command";
        return null;
    }
}
