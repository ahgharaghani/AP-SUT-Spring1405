package controller;

import model.App;

public class AppController {
    public static String getCurrentMenu() {
        return App.getCurrentMenu().toString().toLowerCase();
    }
}
