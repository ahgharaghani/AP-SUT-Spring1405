package model;

import model.animal.*;

// Factory Pattern
public class Factory {
    public static FarmAnimal animalFactory(String name, String type, String role) {
        switch (type.toLowerCase()) {
            case "dog":
                return new Dog(name, type, role);
            case "cow":
                return new Cow(name, type, role);
            case "horse":
                return new Horse(name, type, role);
            case "pig":
                return new Pig(name, type, role);
            case "sheep":
                return new Sheep(name, type, role);
        }

        return null;
    }
}
