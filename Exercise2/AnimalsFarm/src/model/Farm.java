package model;

import dto.WorkResult;
import model.animal.FarmAnimal;
import model.animal.Pig;
import model.enums.*;
import model.ideology.Communism;
import model.ideology.Democracy;
import model.ideology.Dictatorship;
import model.ideology.Ideology;

import java.util.*;

public class Farm {
    public static class GovernorRecord {
        public final int id;
        public final String name;
        public final IdeologyType system;
        public final int startingDay;

        public GovernorRecord(FarmAnimal animal, IdeologyType system, int startingDay) {
            this.id = animal.getID();
            this.name = animal.getName();
            this.system = system;
            this.startingDay = startingDay;
        }
    }

    private List<FarmAnimal> animals;
    private FarmAnimal governor;
    private FarmAnimal sheriff;
    private FarmState state;
    private Ideology ideology;
    private int turn; // Starts from 1
    private int day;
    private boolean dayOver;
    private Map<Integer, IdeologyType> systemVotes;
    private Map<Integer, Integer> totalDailyWorkHours;

    private int tradeRate;

    private List<GovernorRecord> governorRecords;

    Farm() {
        animals = new ArrayList<>();
        turn = 1;
        day = 1;
        dayOver = false;
        state = FarmState.NORMAL_TURN;
        systemVotes = new LinkedHashMap<>();
        totalDailyWorkHours = new HashMap<>();

        tradeRate = 0;

        governorRecords = new ArrayList<>();
    }

    public void addAnimal(FarmAnimal animal) {
        animals.add(animal);
    }

    public boolean shallWeBegin() {
        return animals.size() >= 4;
    }

    public int howManyAnimals() {
        return animals.size();
    }

    public void incrementTurn() {
        boolean dayEnded = false;
        int checkedCount = 0;

        while (checkedCount < animals.size()) {
            this.turn++;
            checkedCount++;

            if (this.turn > animals.size()) {
                this.turn = 1;
                if (!dayEnded) {
                    dayEnded = true;
                    if (state == FarmState.NORMAL_TURN) {
                        dayOver = true;
                        if (day == 1) state = FarmState.SYSTEM_VOTE;
                    }
                }
            }

            FarmAnimal currentAnimal = getCurrentAnimal();
            if (currentAnimal != null && currentAnimal.isAlive()) return;
        }
    }

    public FarmAnimal getCurrentAnimal() {
        try {
            return animals.get(turn - 1);
        } catch (Exception e) {
            return null;
        }
    }

    private FarmAnimal getAnimalByID(int id) {
        for (FarmAnimal a : animals) {
            if (a.getID() == id) return a;
        }

        return null;
    }

    public List<FarmAnimal> getAliveAnimals() {
        List<FarmAnimal> alive = new ArrayList<>();
        for (FarmAnimal a : animals) {
            if (a.isAlive()) alive.add(a);
        }
        return alive;
    }

    public FarmAnimal getAliveAnimalByID(int id) {
        for (FarmAnimal a : animals) {
            if (a.isAlive() && a.getID() == id) return a;
        }

        return null;
    }

    public List<FarmAnimal> getAliveAnimalsByName(String name) {
        List<FarmAnimal> alive = new ArrayList<>();
        for (FarmAnimal a : getAliveAnimals()) {
            if (a.getName().equals(name) && a.isAlive()) alive.add(a);
        }

        return alive;
    }

    public List<FarmAnimal> getWorkerAnimals() {
        List<FarmAnimal> workers = new ArrayList<>();
        for (FarmAnimal a : animals) {
            if (a.getRole() == RoleType.WORKER && a.isAlive()) workers.add(a);
        }

        return workers;
    }

    public void removeAnimalFromFarm(int ID) {
        animals.remove(getAnimalByID(ID));
    }

    public long getAliveCount() {
        return animals.stream().filter(FarmAnimal::isAlive).count();
    }

    public boolean isDayOver() {
        return dayOver;
    }

    public int getDay() { return day; }

    public int nextDay() {
        for (FarmAnimal animal : getAliveAnimals()) {
            animal.setHasWorkedToday(false);
        }

        turn = 1;
        while (!getCurrentAnimal().isAlive()) {
            turn++;
        }
        dayOver = false;
        distributeFood();
        allocateDayWorkingHours();
        return day += 1;
    }

    private void allocateDayWorkingHours() {
        for (FarmAnimal animal : getAliveAnimals()) {
            animal.allocateDayWorkingHours(day + 1);
        }
        totalDailyWorkHours.put(day + 1, 0);
    }

    public FarmState getCurrentState() { return state; }
    public void setState(FarmState s) { state = s; }

    public Ideology getIdeology() {
        return ideology;
    }

    public void castVote(String ideology) {
        castVote(IdeologyType.ofString(ideology));
    }

    public void castVote(IdeologyType ideology) {
        systemVotes.put(getCurrentAnimal().getID(), ideology);
        if (systemVotes.size() == getAliveCount()) {
            finalizeSystem();
            for (FarmAnimal a : animals) {
                if (a.isAlive()) { turn = animals.indexOf(a) + 1; break; }
            }
            return;
        }
        do {
            turn += 1;
        } while (!getCurrentAnimal().isAlive());
    }

    private void finalizeSystem() {
        IdeologyType ideologyType = determineSystem();
        switch (ideologyType) {
            case DEMOCRACY:
                ideology = new Democracy();
                break;
            case COMMUNISM:
                ideology = new Communism();
                break;
            case DICTATORSHIP:
                ideology = new Dictatorship();
                break;
        }
        state = FarmState.GOVERNOR_VOTE;
    }

    private IdeologyType determineSystem() {
        final List<IdeologyType> PRIORITY_ORDER = new ArrayList<>(
                Arrays.asList(
                        IdeologyType.DEMOCRACY,
                        IdeologyType.COMMUNISM,
                        IdeologyType.DICTATORSHIP
                )
        );

        Map<IdeologyType, Integer> voteCounts = new EnumMap<>(IdeologyType.class);
        for (IdeologyType vote : systemVotes.values()) {
            voteCounts.merge(vote, 1, Integer::sum);
        }

        int maxVotes = Collections.max(voteCounts.values());

        return PRIORITY_ORDER.stream()
                .filter(ideology -> voteCounts.getOrDefault(ideology, 0) == maxVotes)
                .findFirst()
                .orElse(null);
    }

    public boolean castGovernorVote(int id) {
        if (animals.stream().noneMatch(animal -> animal.getID() == id && animal.isAlive())) return false;
        int howManyVotes = ideology.castVote(id);
        if (howManyVotes >= getAliveCount()) {
            int governorId = ideology.chooseGovernor();
            governor = getAliveAnimalByID(governorId);
            governor.setRole(RoleType.GOVERNOR);
            state = FarmState.NORMAL_TURN;
            nextDay();
            governorRecords.add(new GovernorRecord(governor, IdeologyType.ofString(ideology.name()), day - 1));
        }
        return true;
    }

    public void finalizeGovernor() {
        int governorId = ideology.selectGovernor(animals, systemVotes);
        governor = getAliveAnimalByID(governorId);
        governor.setRole(RoleType.GOVERNOR);
        state = FarmState.NORMAL_TURN;
        nextDay();
        governorRecords.add(new GovernorRecord(governor, IdeologyType.ofString(ideology.name()), day - 1));
    }

    public FarmAnimal getGovernor() {
        return governor;
    }
    public FarmAnimal getSheriff()  { return sheriff; }

    public void setSheriff(FarmAnimal animal) {
        if (sheriff != null) sheriff.setRole(RoleType.WORKER);
        sheriff = animal;
        if (animal != null) animal.setRole(RoleType.SHERIFF);
    }

    public WorkResult.DeathOutcome handleDeath(FarmAnimal dead) {
        boolean wasGovernor = (dead == governor);
        if (dead == governor) {
            governor = null;
            if (sheriff != null) {
                sheriff.setRole(RoleType.GOVERNOR);
                governor = sheriff;
                sheriff  = null;
                governorRecords.add(new GovernorRecord(governor, IdeologyType.ofString(ideology.name()), day));
            }
        } else if (dead == sheriff) {
            sheriff = null;
        }

        if (getAliveCount() < 4) return wasGovernor ?
                WorkResult.DeathOutcome.GAME_OVER_GOVERNOR : WorkResult.DeathOutcome.GAME_OVER_NORMAL;

        return wasGovernor ? WorkResult.DeathOutcome.DIED_GOVERNOR : WorkResult.DeathOutcome.DIED_NORMAL;
    }

    public void affectPopularityWorkBased(int hours, AnimalType type) {
        for (FarmAnimal animal : animals) {
            animal.affectPopularityWorkBased(hours, type);
        }
    }

    public void affectPopularityRumorBased(FarmAnimal spreader, FarmAnimal target, RumorType rumor, boolean wasTrue) {
        if (wasTrue) {
            for (FarmAnimal animal : animals) {
                animal.affectPopularityTrueRumorBased(governor, target, ideology, rumor);
            }
        } else {
            for (FarmAnimal animal : animals) {
                animal.affectPopularityFalseRumorBased(spreader, ideology, rumor);
            }
        }
    }

    public boolean checkSheriff() {
        return sheriff != null;
    }

    public boolean isCurrentAnimalGovernor() {
        return getCurrentAnimal().getRole() == RoleType.GOVERNOR;
    }

    public boolean shouldChooseSheriff() {
        return !checkSheriff() && isCurrentAnimalGovernor();
    }

    public void setTradeRate(int tradeRate) {
        this.tradeRate = tradeRate;
    }

    public void addToDailyWork(int hours) {
        totalDailyWorkHours.merge(day, hours, Integer::sum);
    }

    public void distributeFood() {
        int rawFood = (int) (totalDailyWorkHours.get(day) * 0.2);
        int distributableFood = rawFood * (100 - tradeRate) / 100;
        int lostFood = rawFood - distributableFood;
        int sheriffCut = (int) (lostFood * 0.5);
        ideology.distributeFood(getAliveAnimals(), distributableFood, totalDailyWorkHours.get(day));
        if (tradeRate > 50 && sheriff != null) sheriff.feed(sheriffCut);
    }

    public String changeRule(String newRule, String oldRule) {
        return ideology.changeRule(newRule, oldRule, getAliveAnimals());
    }

    public List<GovernorRecord> getGovernorsHistory() {
        return governorRecords;
    }

    public void applyRebellion(FarmAnimal newGovernor, IdeologyType newSystemType) {
        governor.setRole(RoleType.WORKER);
        if (sheriff != null) {
            sheriff.setRole(RoleType.WORKER);
            sheriff = null;
        }
        governor = newGovernor;
        newGovernor.setRole(RoleType.GOVERNOR);

        switch (newSystemType) {
            case DEMOCRACY:    ideology = new Democracy();    break;
            case COMMUNISM:    ideology = new Communism();    break;
            case DICTATORSHIP: ideology = new Dictatorship(); break;
        }

        governorRecords.add(new GovernorRecord(newGovernor, newSystemType, day));
    }

    public List<Rules> getRules() {
        if (ideology == null) {
            return Arrays.asList(Rules.values());
        }
        return ideology.getRules();
    }
}