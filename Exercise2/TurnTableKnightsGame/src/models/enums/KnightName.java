package models.enums;

public enum KnightName {
    ARTHUR("Arthur"),
    MORDRED("Mordred"),
    LANCELOT("Lancelot"),
    GALAHAD("Galahad"),
    MORGAN("Morgan"),
    MERLIN("Merlin");

    private final String name;

    KnightName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static KnightName fromString(String name) {
        for (KnightName knightName : KnightName.values()) {
            if (knightName.name.equalsIgnoreCase(name)) {
                return knightName;
            }
        }

        return null;
    }
}
