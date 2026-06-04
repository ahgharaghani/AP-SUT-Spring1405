package models.enums;

public enum KnightClass {
    COMMANDER, WARRIOR, HEALER, MAGE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}