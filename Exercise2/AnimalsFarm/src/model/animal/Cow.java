package model.animal;

import model.enums.AnimalType;
import model.enums.IdeologyType;

import java.util.HashMap;

public class Cow extends FarmAnimal {
    public Cow(String name, String type, String role) {
        super(name, type, role);
        this.speciesOpinion = new HashMap<>();
        speciesOpinion.put(AnimalType.DOG, 30.0);
        speciesOpinion.put(AnimalType.PIG, 30.0);
        speciesOpinion.put(AnimalType.COW, 70.0);
        speciesOpinion.put(AnimalType.HORSE, 70.0);
        speciesOpinion.put(AnimalType.SHEEP, 70.0);

        hungerRate = 2.5;
    }
}
