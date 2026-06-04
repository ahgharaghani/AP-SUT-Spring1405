package model.animal;

import model.enums.AnimalType;
import model.enums.IdeologyType;
import model.enums.RoleType;
import model.enums.RumorType;
import model.ideology.Ideology;

import java.util.*;

public abstract class FarmAnimal {
    private static int incrementer = 1;

    public static void resetIdCounter() {
        incrementer = 1;
    }

    protected int ID;
    protected String name;
    protected AnimalType type;
    protected RoleType role;
    protected Map<IdeologyType, Double> ideologiesOpinion;
    protected Map<AnimalType, Double> speciesOpinion;

    protected double fullness;

    protected boolean rebellious;
    protected boolean liar;
    protected boolean corrupt;
    protected boolean lazy;

    protected Map<Integer, Integer> hoursWorkedDaily;

    protected double hungerRate;

    protected boolean alive;

    protected int falseRumorsSpread;

    protected int governorships;

    protected boolean hasWorkedToday;

    public FarmAnimal(String name, AnimalType type, RoleType role) {
        this.ID = incrementer++;
        this.name = name;
        this.type = type;
        this.role = role;
        this.ideologiesOpinion = new HashMap<>();
        ideologiesOpinion.put(IdeologyType.COMMUNISM, 50.0);
        ideologiesOpinion.put(IdeologyType.DEMOCRACY, 50.0);
        ideologiesOpinion.put(IdeologyType.DICTATORSHIP, 50.0);

        fullness = 100.0; // ?

        rebellious = false;
        liar = false;
        corrupt = false;
        lazy = false;

        hoursWorkedDaily = new LinkedHashMap<>();
        hoursWorkedDaily.put(-2, 0);
        hoursWorkedDaily.put(-1, 0);
        hoursWorkedDaily.put(0 , 0);
        hoursWorkedDaily.put(1, 0);

        alive = true;

        falseRumorsSpread = 0;

        governorships = 0;

        hasWorkedToday = false;
    }

    public FarmAnimal(String name, String type, String role) {
        this(name, AnimalType.ofString(type), RoleType.ofString(role));
    }

    public int getID() { return ID; }
    public String getName() { return name; }
    public AnimalType getType() { return type; }
    public RoleType getRole() { return role; }
    public double getFullness() { return fullness; }
    public boolean isRebellious() { return rebellious; }
    public boolean isLiar() { return liar; }
    public boolean isCorrupt() { return corrupt; }
    public Map<IdeologyType, Double> getIdeologiesOpinion() { return ideologiesOpinion; }
    public Map<AnimalType, Double> getSpeciesOpinion() { return speciesOpinion; }
    public boolean isAlive() { return alive; }
    public int getFalseRumorsSpread() { return falseRumorsSpread; }
    public int getGovernorships() { return governorships; }

    public boolean isLazy(int currentDay) {
        int day = currentDay;
        if (!hasWorkedToday || getWorkHoursOnDay(currentDay) == 0) {
            day = currentDay - 1;
        }
        int sum = getWorkHoursOnDay(day)
                + getWorkHoursOnDay(day - 1)
                + getWorkHoursOnDay(day - 2);

        return sum < 10;
    }

    private int getWorkHoursOnDay(int day) {
        return hoursWorkedDaily.getOrDefault(day, 0);
    }

    public void setHasWorkedToday(boolean hasWorkedToday) {
        this.hasWorkedToday = hasWorkedToday;
    }

    public void setRole(RoleType role) {
        this.role = role;
        if (role == RoleType.GOVERNOR) governorships += 1;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public void setRebellious(boolean rebellious) {
        this.rebellious = rebellious;
    }

    public void setLiar(boolean liar) {
        this.liar = liar;
    }

    public void setCorrupt(boolean corrupt) {
        this.corrupt = corrupt;
    }

    public int getHoursWorkedDay1() {
        return hoursWorkedDaily.get(1);
    }

    public int getHoursWorkedLastDay() {
        return hoursWorkedDaily.values().stream().reduce((first, second) -> second).orElse(null);
    }

    public double getOpinionOf(IdeologyType ideologyType) {
        return ideologiesOpinion.get(ideologyType);
    }

    public double getOpinionOf(AnimalType animalType) {
        return speciesOpinion.get(animalType);
    }

    public boolean work(int currentDay, int hours) {
        hasWorkedToday = true;
        hoursWorkedDaily.merge(currentDay, hours, Integer::sum);
        fullness -= Math.min((double) hours * hungerRate, fullness);
        if (fullness == 0) alive = false;
        return alive;
    }

    public void affectPopularityWorkBased(int hours, AnimalType type) {
        Double addition = hours * type.getPopRate();
        Double currentPop = speciesOpinion.get(type);
        speciesOpinion.replace(type, Math.min(100, currentPop + addition));
    }

    public boolean spreadRumor(FarmAnimal target, RumorType rumor, int currentDay) {
        if (rumor == RumorType.LAZY) {
            if (target.isLazy(currentDay)) return true;
            liar = true;
            falseRumorsSpread += 1;
            return false;
        } else if (rumor == RumorType.CORRUPT) {
            if (target.isCorrupt()) {
                return true;
            } else {
                liar = true;
                falseRumorsSpread += 1;
                return false;
            }
        } else if (rumor == RumorType.REBELLIOUS) {
            if (target.isRebellious()) return true;
            else {
                liar = true;
                falseRumorsSpread += 1;
                return false;
            }
        } else if (rumor == RumorType.LIAR) {
            if (target.isLiar()) {
                return true;
            }
            liar = true;
            falseRumorsSpread += 1;
            return false;
        }
        return false;
    }

    public void affectPopularityTrueRumorBased(FarmAnimal governor, FarmAnimal target,
                                               Ideology ideology, RumorType rumor) {
        double lossImmunityMult = 1;
        if (ideology != null) {
            if (ideology.isImmune(target)) {
                lossImmunityMult = 0.5;
            }
        }

        double loss = lossImmunityMult * rumor.getBaseEffect();
        AnimalType targetType = target.getType();
        double currentPop = speciesOpinion.get(targetType);
        speciesOpinion.replace(targetType, Math.max(0, currentPop - loss));

        if (governor != null) {
            double additionImmunityMult;
            if (ideology != null && ideology.isImmune(governor)) {
                additionImmunityMult = 1.5;
            } else additionImmunityMult = 1;

            double addition = additionImmunityMult * rumor.getBaseEffect();
            AnimalType governorType = governor.getType();
            double currentGovernorPop = speciesOpinion.get(governorType);
            speciesOpinion.replace(governorType, Math.min(100, currentGovernorPop + addition));
        }

        if (ideology != null) {
            double ideologyAddition = (double) rumor.getBaseEffect() / 2;
            IdeologyType ideologyType = IdeologyType.ofString(ideology.name());
            double currentIdeologyPop = ideologiesOpinion.get(ideologyType);
            ideologiesOpinion.replace(ideologyType, Math.min(100, currentIdeologyPop + ideologyAddition));
        }
    }

    public void affectPopularityFalseRumorBased(FarmAnimal spreader, Ideology ideology, RumorType rumor) {
        double lossImmunityMult;
        if (ideology != null && ideology.isImmune(spreader)) {
            lossImmunityMult = 0.5;
        } else lossImmunityMult = 1;

        double loss = lossImmunityMult * rumor.getBaseEffect();
        AnimalType targetType = spreader.getType();
        double currentPop = speciesOpinion.get(targetType);
        speciesOpinion.replace(targetType, Math.max(0, currentPop - loss));

        if (ideology != null) {
            double ideologyLoss = (double) rumor.getBaseEffect();
            IdeologyType ideologyType = IdeologyType.ofString(ideology.name());
            double currentIdeologyPop = ideologiesOpinion.get(ideologyType);
            ideologiesOpinion.replace(ideologyType, Math.max(0, currentIdeologyPop - ideologyLoss));
        }
    }

    public void feed(int foodShare) {
        fullness = Math.min(100, fullness + foodShare);
    }

    public void allocateDayWorkingHours(int day) {
        hoursWorkedDaily.putIfAbsent(day, 0);
    }

    public void affectPopularityRuleBased(IdeologyType ideologyType) {
        double pop = ideologiesOpinion.get(ideologyType);
        if (pop > 30) ideologiesOpinion.replace(ideologyType, Math.min(100, pop * 1.4));
        else ideologiesOpinion.replace(ideologyType, pop * 0.8);
    }

    public boolean isRebelEligible(IdeologyType currentSystem) {
        return fullness < 30 && getOpinionOf(currentSystem) < 40;
    }

    public boolean isCandidacyEligible(IdeologyType suggestedSystem, FarmAnimal rebellionLeader) {
        return role != RoleType.GOVERNOR &&
                getOpinionOf(suggestedSystem) >= 50 &&
                rebellionLeader.getOpinionOf(type) >= 50;
    }

    public boolean doesSupportRebellion(IdeologyType currentSystemType) {
        return fullness < 50 && getOpinionOf(currentSystemType) < 30;
    }
}