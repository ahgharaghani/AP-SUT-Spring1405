package models.enums;

public enum Menu {
    MAIN("main menu"),
    AUTH("auth menu"),
    EXPLORE("explore menu"),
    GUEST("guest menu"),
    HOST("host menu");

    private final String displayName;

    Menu(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
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