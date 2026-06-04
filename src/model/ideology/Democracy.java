package model.ideology;

import model.Rule;
import model.animal.FarmAnimal;
import model.enums.IdeologyType;
import model.enums.Rules;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Democracy implements Ideology {
    private List<Integer> governorVotes = new ArrayList<>();
    private List<Rules> rules;

    public Democracy() {
        rules = new ArrayList<>(Arrays.asList(Rules.values()));
    }

    public String name() {
        return "Democracy";
    }

    public int castVote(int id) {
        governorVotes.add(id);
        return governorVotes.size();
    }

    public int chooseGovernor() {
        return governorVotes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Comparator
                        .comparingLong(Map.Entry<Integer, Long>::getValue)
                        .thenComparingInt(e -> -e.getKey()))
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    public int selectGovernor(List<FarmAnimal> animals, Map<Integer, IdeologyType> systemVotes) {
        return -1;
    }

    @Override
    public boolean isImmune(FarmAnimal target) {
        return false;
    }

    @Override
    public void distributeFood(List<FarmAnimal> aliveAnimals, int distributableFood, int totalWorkHours) {
        if (totalWorkHours == 0) return;
        for (FarmAnimal animal : aliveAnimals) {
            int foodShare = (int) (distributableFood * (animal.getHoursWorkedLastDay() / (double) totalWorkHours));
            animal.feed(foodShare);
        }
    }

    @Override
    public String changeRule(String newRule, String oldRule, List<FarmAnimal> aliveAnimals) {
        int total = aliveAnimals.size();
        int ayes = 0;
        for (Rules rule : rules) {
            if (rule.getDescription().equals(oldRule)) {
                for (FarmAnimal animal : aliveAnimals) {
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
