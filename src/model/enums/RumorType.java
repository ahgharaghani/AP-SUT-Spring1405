package model.enums;

public enum RumorType {
    LAZY(6),
    CORRUPT(10),
    REBELLIOUS(8),
    LIAR(7);

    private final int baseEffect;

    RumorType(int baseEffect) {
        this.baseEffect = baseEffect;
    }

    public static RumorType ofString(String typeStr) {
        for (RumorType rumor : RumorType.values()) {
            if (rumor.name().equalsIgnoreCase(typeStr)) return rumor;
        }

        return null;
    }

    public int getBaseEffect() {
        return baseEffect;
    }
}
