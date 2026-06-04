package model.enums;

public enum IdeologyType {
    COMMUNISM, DEMOCRACY, DICTATORSHIP;

    public static IdeologyType ofString(String ideologyType) {
        for (IdeologyType type : IdeologyType.values()) {
            if (type.name().equalsIgnoreCase(ideologyType)) return type;
        }
        return null;
    }
}
