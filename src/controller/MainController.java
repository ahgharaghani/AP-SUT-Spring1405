package controller;

import model.App;
import model.Factory;
import model.Farm;
import model.animal.FarmAnimal;
import model.enums.AnimalType;
import model.enums.Menu;
import model.enums.RoleType;

import java.util.regex.Pattern;

public class MainController {
    private static final String ANIMAL_NAME_PATTERN = "^[a-zA-Z]{3,}$";

    public static String addAnimalToFarm(String animalName, String animalType, String animalRole) {
        if (!Pattern.matches(ANIMAL_NAME_PATTERN, animalName)) return "Invalid name format.";
        AnimalType type = AnimalType.ofString(animalType);
        if (type == null)  return "Invalid animal type.";
        RoleType role = RoleType.ofString(animalRole);
        if (role == null) return "Invalid role.";
        if (role == RoleType.GOVERNOR || role == RoleType.SHERIFF) return "There is no governor or sheriff yet!";
        FarmAnimal newAnimal = Factory.animalFactory(animalName, animalType, animalRole);
        Farm currentFarm = App.getCurrentFarm();
        currentFarm.addAnimal(newAnimal);
        return "Animal added: ID=" + newAnimal.getID()
                + ", Name=" + animalName
                + ", Type=" + animalType.toLowerCase()
                + ", Role=" + animalRole.toLowerCase();
    }

    public static String startGame() {
        Farm currentFarm = App.getCurrentFarm();
        if (!currentFarm.shallWeBegin()) return "Not enough animals to start the game. Current players: "
                + currentFarm.howManyAnimals()
                + ". Add more animals.";

        App.setCurrentMenu(Menu.GAME);
        return "Bazi Shoro shod.";
    }
}
