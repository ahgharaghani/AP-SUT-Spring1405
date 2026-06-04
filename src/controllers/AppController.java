package controllers;

import models.App;

public class AppController {
    public static String getCurrentMenu() {
        return App.getCurrentMenu().getDisplayName();
    }
}
