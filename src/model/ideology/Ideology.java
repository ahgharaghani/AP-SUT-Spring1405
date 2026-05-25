package model.ideology;

import model.animal.FarmAnimal;
import model.enums.IdeologyType;

import java.util.List;
import java.util.Map;

public interface Ideology {
    String name();
    int castVote(int id);
    int chooseGovernor();
    int selectGovernor(List<FarmAnimal> animals, Map<Integer, IdeologyType> systemVotes);
    boolean isImmune(FarmAnimal target);
    void distributeFood(List<FarmAnimal> aliveAnimals, int distributableFood, int totalWorkHours);
    String changeRule(String newRule, String oldRule, List<FarmAnimal> aliveAnimals);
}
