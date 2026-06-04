package model;

import model.animal.FarmAnimal;
import model.enums.Menu;

public class App {
    private static Farm currentFarm = new Farm();
    private static Menu currentMenu = Menu.MAIN;

    public static Farm getCurrentFarm() { return currentFarm; }
    public static Menu getCurrentMenu() { return currentMenu; }

    public static void setCurrentMenu(Menu menu) { currentMenu = menu; }

    public static void reset() {
        FarmAnimal.resetIdCounter();
        currentFarm = new Farm();
        currentMenu = Menu.MAIN;
    }
}
