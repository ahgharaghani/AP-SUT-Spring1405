package model.animal;

import model.enums.AnimalType;

import java.util.HashMap;

public class Pig extends FarmAnimal {
    public Pig(String name, String type, String role) {
        super(name, type, role);
        this.speciesOpinion = new HashMap<>();
        speciesOpinion.put(AnimalType.DOG, 70.0);
        speciesOpinion.put(AnimalType.PIG, 70.0);
        speciesOpinion.put(AnimalType.COW, 30.0);
        speciesOpinion.put(AnimalType.HORSE, 30.0);
        speciesOpinion.put(AnimalType.SHEEP, 30.0);

        hungerRate = 0.8;
    }
}
