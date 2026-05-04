package controllers;

import models.App;
import models.enums.Menu;

public class MainController {
    public static String quitApp() {
        if (App.getCurrentMenu() != Menu.MAIN) return "invalid command";
        return null;
    }
}
