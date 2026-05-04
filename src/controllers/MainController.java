package controllers;

import models.App;
import models.Menu;

public class MainController {
    public static String quitApp() {
        if (App.getCurrentMenu() != Menu.MAIN) return "invalid command";
        return null;
    }
}
