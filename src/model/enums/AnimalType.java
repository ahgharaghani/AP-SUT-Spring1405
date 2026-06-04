package model.enums;

public enum AnimalType {
    DOG(1.8),
    HORSE(1.5),
    PIG(2.0),
    SHEEP(1.0),
    COW(1.2);

    private Double popRate;

    AnimalType(Double popRate) {
        this.popRate = popRate;
    }

    public static AnimalType ofString(String animalType) {
        for (AnimalType type : AnimalType.values()) {
            if (type.name().equalsIgnoreCase(animalType)) return type;
        }
        return null;
    }

    public Double getPopRate() {
        return popRate;
    }
}
