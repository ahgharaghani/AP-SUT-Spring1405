package model.ideology;

import model.Rule;
import model.animal.FarmAnimal;
import model.enums.AnimalType;
import model.enums.IdeologyType;
import model.enums.RoleType;
import model.enums.Rules;

import java.util.*;
import java.util.stream.Collectors;

public class Dictatorship implements Ideology {
    private List<Rules> rules;

    public Dictatorship() {
        rules =  new ArrayList<>(Arrays.asList(Rules.values()));
    }

    public String name() { return "Dictatorship"; }

    public int castVote(int id) { return 0; }
    public int chooseGovernor() { return -1; }

    public int selectGovernor(List<FarmAnimal> animals, Map<Integer, IdeologyType> systemVotesByOrder) {
        List<Integer> dictatorshipVoterIds = new ArrayList<>(systemVotesByOrder.keySet())
                .stream()
                .filter(id -> systemVotesByOrder.get(id) == IdeologyType.DICTATORSHIP)
                .collect(Collectors.toList());

        return dictatorshipVoterIds.stream()
                .flatMap(id -> animals.stream().filter(a -> a.getID() == id))
                .max(Comparator
                        .comparingDouble((FarmAnimal c) -> computePopularity(c.getType(), animals))
                        .thenComparing(a -> dictatorshipVoterIds.indexOf(a.getID()), Comparator.reverseOrder()))
                .map(FarmAnimal::getID)
                .orElse(-1);
    }

    private double computePopularity(AnimalType type, List<FarmAnimal> animals) {
        return animals.stream()
                .mapToDouble(a -> a.getSpeciesOpinion().getOrDefault(type, 0.0))
                .sum();
    }

    @Override
    public boolean isImmune(FarmAnimal target) {
        return target.getRole() == RoleType.GOVERNOR;
    }

    @Override
    public void distributeFood(List<FarmAnimal> aliveAnimals, int distributableFood, int totalWorkHours) {
        int governorBonus = distributableFood / 4;
        int individualFoodShare = (distributableFood - governorBonus) / aliveAnimals.size();

        for (FarmAnimal animal : aliveAnimals) {
            animal.feed(individualFoodShare);
            if (animal.getRole() == RoleType.GOVERNOR) animal.feed(governorBonus);
        }
    }

    @Override
    public String changeRule(String newRule, String oldRule, List<FarmAnimal> aliveAnimals) {
        for (Rules rule : rules) {
            if (rule.getDescription().equals(oldRule)) {
                rule.changeRule(newRule, IdeologyType.ofString(name()));
                for (FarmAnimal animal : aliveAnimals) {
                    animal.affectPopularityRuleBased(IdeologyType.ofString(name()));
                }
                return "Rule successfully changed to: " + newRule;
            }
        }

        return "There is no such rule!";
    }

    @Override
    public List<Rules> getRules() { return rules; }
}
