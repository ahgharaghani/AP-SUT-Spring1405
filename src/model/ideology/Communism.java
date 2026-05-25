package model.ideology;

import model.Farm;
import model.Rule;
import model.animal.FarmAnimal;
import model.enums.AnimalType;
import model.enums.IdeologyType;
import model.enums.RoleType;
import model.enums.Rules;

import java.util.*;

public class Communism implements Ideology {
    private List<Rules> rules;

    public Communism() {
        rules = new ArrayList<>(Arrays.asList(Rules.values()));
    }


    public int castVote(int id) { return 0; }
    public int chooseGovernor() { return -1; }

    public String name() { return "Communism"; }

    public int selectGovernor(List<FarmAnimal> animals, Map<Integer, IdeologyType> systemVotes) {
        return animals.stream()
                .filter(a -> a.getRole() == RoleType.WORKER)
                .max(Comparator
                        .comparingDouble((FarmAnimal candidate) -> computePopularity(candidate.getType(), animals))
                        .thenComparingInt(a -> -a.getHoursWorkedDay1())
                        .thenComparing(Comparator.comparingInt(FarmAnimal::getID).reversed()))
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
        return target.getRole() == RoleType.WORKER;
    }

    @Override
    public void distributeFood(List<FarmAnimal> aliveAnimals, int distributableFood, int totalWorkHours) {
        int foodShare = distributableFood / aliveAnimals.size();
        for (FarmAnimal animal : aliveAnimals) {
            animal.feed(foodShare);
        }
    }

    @Override
    public String changeRule(String newRule, String oldRule, List<FarmAnimal> aliveAnimals) {
        List<FarmAnimal> workers = new ArrayList<>();
        for (FarmAnimal animal : aliveAnimals) {
            if (animal.getRole() == RoleType.WORKER) workers.add(animal);
        }


        int total = workers.size();
        int ayes = 0;
        for (Rules rule : rules) {
            if (rule.getDescription().equals(oldRule)) {
                for (FarmAnimal animal : workers) {
                    double currentSystemOpinion = animal.getOpinionOf(IdeologyType.ofString(name()));
                    double ruleSystemOpinion = animal.getOpinionOf(rule.getIdeology());
                    if (currentSystemOpinion >= ruleSystemOpinion) ayes += 1;
                }
                if (ayes > (total / 2)) {
                    rule.changeRule(newRule, IdeologyType.ofString(name()));
                    for (FarmAnimal animal : aliveAnimals) {
                        animal.affectPopularityRuleBased(IdeologyType.ofString(name()));
                    }
                    return "Rule successfully changed to: " + newRule;
                }
                return "Rule didn't change. Just " + ayes + " people voted for that.";
            }
        }

        return "There is no such rule!";
    }

    @Override
    public List<Rules> getRules() { return rules; }
}
