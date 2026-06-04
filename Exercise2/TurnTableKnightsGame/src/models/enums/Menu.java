package models.enums;

public enum Menu {
    MAIN("main menu"),
    SIGNUP("signup menu"),
    GAME("game menu"),
    SCOREBOARD("scoreboard menu");

    private final String displayName;

    Menu(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public static Menu fromString(String name) {
        for (Menu menu : Menu.values()) {
            if (menu.displayName.equalsIgnoreCase(name)) {
                return menu;
            }
        }
        return null;
    }
}
