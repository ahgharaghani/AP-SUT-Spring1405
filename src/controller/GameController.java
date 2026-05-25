package controller;

import dto.AnimalDetailsDTO;
import dto.GovernorHistoryDTO;
import dto.RebellionResultDTO;
import dto.WorkResult;
import model.App;
import model.Farm;
import model.animal.FarmAnimal;
import model.enums.FarmState;
import model.enums.IdeologyType;
import model.enums.RoleType;
import model.enums.RumorType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameController {
    public static String getCurrentState() {
        return App.getCurrentFarm().getCurrentState().toString();
    }

    public static String whoIsPlaying() {
        FarmAnimal currentAnimal = App.getCurrentFarm().getCurrentAnimal();
        return currentAnimal.getName() + " [" + currentAnimal.getID() + "] >";
    }

    public static List<AnimalDetailsDTO> getAnimalDetails() {
        Farm currentFarm = App.getCurrentFarm();
        List<FarmAnimal> animals = currentFarm.getAliveAnimals();
        List<AnimalDetailsDTO> dtos = new ArrayList<>();

        for (FarmAnimal animal : animals) {
            if (!animal.isAlive()) continue;
            dtos.add(
                    new AnimalDetailsDTO(
                            animal.getID(),
                            animal.getName(),
                            animal.getType().toString().toLowerCase(),
                            animal.getRole().toString().toLowerCase(),
                            animal.getFullness(),
                            animal.isRebellious(),
                            animal.isLiar(),
                            animal.isCorrupt(),
                            animal.isLazy(),
                            convertOpinionMaps(animal.getIdeologiesOpinion()),
                            convertOpinionMaps(animal.getSpeciesOpinion())
                    )
            );
        }

        return dtos;
    }

    public static int checkIfDayOver() {
        Farm farm = App.getCurrentFarm();
        if (!farm.isDayOver()) return 0;
        int completedDay = farm.getDay();
        if (completedDay == 1) return completedDay;
        farm.nextDay();
        return completedDay;
    }


    public static String castVote(String system) {
        Farm currentFarm = App.getCurrentFarm();
        IdeologyType ideology = IdeologyType.ofString(system);
        if (ideology == null) return "Invalid political system.";
        currentFarm.castVote(ideology);

        return "Your vote registered successfully";
    }

    public static String castGovernorVote(String idStr) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return "Animal not found.";
        }

        Farm currentFarm = App.getCurrentFarm();
        boolean result = currentFarm.castGovernorVote(id);

        if (!result) return "Animal not found.";
        if (currentFarm.getCurrentState() == FarmState.GOVERNOR_VOTE) {
            currentFarm.incrementTurn();
        }
        return "Your vote registered successfully";
    }

    public static String getSystem() {
        Farm currentFarm = App.getCurrentFarm();
        return currentFarm.getIdeology().name();
    }

    private static <T extends Enum<T>> Map<String, Double> convertOpinionMaps(Map<T, Double> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().name().toLowerCase(),
                        Map.Entry::getValue
                ));
    }

    public static void finalizeGovernor() {
        Farm currentFarm = App.getCurrentFarm();
        currentFarm.finalizeGovernor();
    }

    public static String getGovernorName() {
        Farm currentFarm = App.getCurrentFarm();
        return currentFarm.getGovernor().getName();
    }

    public static WorkResult work(String hoursStr) {
        Farm currentFarm = App.getCurrentFarm();
        FarmAnimal currentAnimal = currentFarm.getCurrentAnimal();

        int hours;
        try {
            hours = Integer.parseInt(hoursStr);
            if (hours <= 0) {
                currentFarm.incrementTurn();
                return WorkResult.error("Invalid working hours.");
            }
        } catch (NumberFormatException e) {
            currentFarm.incrementTurn();
            return WorkResult.error("Invalid working hours.");
        }

        boolean survived = currentAnimal.work(currentFarm.getDay(), hours);
        currentFarm.addToDailyWork(hours);
        if (survived) {
            currentFarm.incrementTurn();
            currentFarm.affectPopularityWorkBased(hours, currentAnimal.getType());
            return WorkResult.success();
        }

        WorkResult.DeathOutcome outcome = currentFarm.handleDeath(currentAnimal);

        if (outcome == WorkResult.DeathOutcome.GAME_OVER) {
            App.reset();
            return WorkResult.gameOver(currentAnimal.getName(), currentAnimal.getID());
        }

        currentFarm.incrementTurn();

        if (outcome == WorkResult.DeathOutcome.DIED_GOVERNOR) {
            currentFarm.incrementTurn();
            FarmAnimal newGovernor = currentFarm.getGovernor();
            return WorkResult.diedGovernor(currentAnimal.getName(), currentAnimal.getID(),
                    newGovernor.getName(), newGovernor.getID());
        }

        currentFarm.incrementTurn();
        return WorkResult.diedNormal(currentAnimal.getName(), currentAnimal.getID());
    }

    public static String spreadRumor(String targetIdStr, String typeStr) {
        Farm currentFarm = App.getCurrentFarm();
        FarmAnimal currentAnimal = currentFarm.getCurrentAnimal();

        int targetID;
        try {
            targetID = Integer.parseInt(targetIdStr);
        } catch (NumberFormatException e) {
            currentFarm.incrementTurn();
            return "Target not found.";
        }

        if (currentAnimal.getRole() != RoleType.PROPAGANDA) {
            currentFarm.incrementTurn();
            return "Only propaganda can spread rumors.";
        }
        FarmAnimal target = currentFarm.getAliveAnimalByID(targetID);
        if (target == null) {
            currentFarm.incrementTurn();
            return "Target not found.";
        }
        RumorType rumor = RumorType.ofString(typeStr);
        if (rumor == null) {
            currentFarm.incrementTurn();
            return "Invalid rumor type.";
        }
        if (rumor == RumorType.CORRUPT && target.getRole() != RoleType.SHERIFF){
            currentFarm.incrementTurn();
            return "Corrupt rumor just can be spread for Sheriff.";
        }
        if (rumor == RumorType.LIAR && target.getRole() != RoleType.PROPAGANDA) {
            currentFarm.incrementTurn();
            return "Liar rumor just can be spread for Propaganda.";
        }

        boolean result = currentAnimal.spreadRumor(target, rumor);
        currentFarm.affectPopularityRumorBased(currentAnimal, target, rumor, result);

        currentFarm.incrementTurn();
        return "The rumor spread successfully. Result: " + result;
    }

    public static boolean shouldChooseSheriff() {
        return App.getCurrentFarm().shouldChooseSheriff();
    }

    public static String chooseSheriff(String idStr) {
        Farm currentFarm = App.getCurrentFarm();
        FarmAnimal currentAnimal = currentFarm.getCurrentAnimal();
        if (currentAnimal.getRole() != RoleType.GOVERNOR) {
            currentFarm.incrementTurn();
            return "Only the governor can appoint a sheriff.";
        }

        int targetID;
        try {
            targetID = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return "Animal not found.";
        }

        FarmAnimal target = currentFarm.getAliveAnimalByID(targetID);
        if (target == null)
            return "Animal not found.";

        currentFarm.setSheriff(target);
        currentFarm.incrementTurn();
        return "Sheriff chosen: ID=" + target.getID() + ", Name=" + target.getName();
    }

    public static String setTradeRate(String rateStr) {
        Farm currentFarm = App.getCurrentFarm();
        FarmAnimal currentAnimal =  currentFarm.getCurrentAnimal();

        int rate;
        try {
            rate = Integer.parseInt(rateStr);
            if (rate > 100 || rate < 0){
                currentFarm.incrementTurn();
                return "Invalid percent value.";
            }
        } catch (NumberFormatException e) {
            currentFarm.incrementTurn();
            return "Rate number must be integer.";
        }

        if (currentAnimal.getRole() != RoleType.SHERIFF){
            currentFarm.incrementTurn();
            return "Only sheriff can set trade rate.";
        }

        currentFarm.setTradeRate(rate);
        if (rate > 50)
            currentAnimal.setCorrupt(true);

        currentFarm.incrementTurn();
        return "Trade rate set by sheriff: " + rate + "%";
    }

    public static String changeRule(String newRule, String oldRule) {
        Farm currentFarm = App.getCurrentFarm();
        FarmAnimal currentAnimal = currentFarm.getCurrentAnimal();

        if (currentAnimal.getRole() != RoleType.GOVERNOR) {
            currentFarm.incrementTurn();
            return "Only governor can change rules.";
        }

        String result = currentFarm.changeRule(newRule, oldRule);
        currentFarm.incrementTurn();
        return result;
    }

    public static List<GovernorHistoryDTO> getGovernorsHistory() {
        Farm currentFarm =  App.getCurrentFarm();
        List<Farm.GovernorRecord> governorRecords = currentFarm.getGovernorsHistory();
        if (governorRecords.isEmpty()) return null;

        List<GovernorHistoryDTO> dtos = new ArrayList<>();
        for (Farm.GovernorRecord record : governorRecords) {
            dtos.add(new GovernorHistoryDTO(record));
        }

        return dtos;
    }

    public static void backToMainMenu() {
        App.reset();
    }

    public static RebellionResultDTO rebel(String candidateName, String tong) {
        Farm currentFarm = App.getCurrentFarm();
        FarmAnimal currentAnimal = currentFarm.getCurrentAnimal();

        currentAnimal.setRebellious(true);

        if (currentAnimal.getRole() != RoleType.WORKER) {
            currentFarm.incrementTurn();
            return RebellionResultDTO.notAWorker();
        }

        List<FarmAnimal> candidates = currentFarm.getAliveAnimalsByName(candidateName);
        if (candidates.isEmpty()) {
            currentFarm.incrementTurn();
            return RebellionResultDTO.candidateNotFound();
        }

        IdeologyType tongType = IdeologyType.ofString(tong);
        if (tongType == null) {
            currentFarm.incrementTurn();
            return RebellionResultDTO.invalidTong();
        }

        IdeologyType currentSystemType = IdeologyType.ofString(currentFarm.getIdeology().name());
        if (!currentAnimal.isRebelEligible(currentSystemType)) {
            currentFarm.incrementTurn();
            return RebellionResultDTO.conditionsNotMet();
        }

        if (candidates.size() == 1 && candidates.get(0).getRole() == RoleType.GOVERNOR) {
            currentFarm.incrementTurn();
            return RebellionResultDTO.onlyGovernorSuggested();
        }

        List<FarmAnimal> eligibleCandidates = new ArrayList<>();
        for (FarmAnimal candidate : candidates) {
            if (candidate.isCandidacyEligible(tongType, currentAnimal))
                eligibleCandidates.add(candidate);
        }

        if (eligibleCandidates.isEmpty()) {
            currentFarm.incrementTurn();
            return RebellionResultDTO.noValidCandidate();
        }

        List<FarmAnimal> workers = currentFarm.getWorkerAnimals();
        int totalWorkers = workers.size();
        int ayes = 0;

        for (FarmAnimal worker : workers) {
            if (worker.doesSupportRebellion(currentSystemType))
                ayes += 1;
        }

        if (ayes <= totalWorkers / 2) {
            currentFarm.incrementTurn();
            return RebellionResultDTO.notEnoughSupport();
        }

        Map<FarmAnimal, Integer> voteCounts = holdRebellionElection(eligibleCandidates, workers, currentSystemType, tongType);

        FarmAnimal winner = getWinnerOfElections(eligibleCandidates, voteCounts);

        currentFarm.applyRebellion(winner, tongType);
        currentFarm.incrementTurn();

        List<RebellionResultDTO.RebelCandidateDTO> candidateDTOs = new ArrayList<>();
        for (FarmAnimal candidate : eligibleCandidates) {
            candidateDTOs.add(new RebellionResultDTO.RebelCandidateDTO(candidate));
        }

        List<RebellionResultDTO.RebelVoteResultDTO> voteResultDTOs = new ArrayList<>();
        for (FarmAnimal candidate : eligibleCandidates) {
            voteResultDTOs.add(new RebellionResultDTO.RebelVoteResultDTO(
                    candidate, voteCounts.get(candidate)
            ));
        }

        return RebellionResultDTO.succeeded(
                candidateDTOs,
                voteResultDTOs,
                winner.getID(),
                winner.getName(),
                tongType.name().toLowerCase()
        );
    }

    private static Map<FarmAnimal, Integer> holdRebellionElection(List<FarmAnimal> eligibleCandidates,
                                                           List<FarmAnimal> workers,
                                                           IdeologyType currentSystemType,
                                                           IdeologyType tongType
                                                           ) {
        Map<FarmAnimal, Integer> voteCounts = new HashMap<>();
        for (FarmAnimal candidate : eligibleCandidates) {
            voteCounts.put(candidate, 0);
        }

        for (FarmAnimal worker : workers) {
            if (!worker.doesSupportRebellion(currentSystemType)) continue;

            FarmAnimal selectedCandidate = null;
            double maxPopularity = -1;

            for (FarmAnimal candidate : eligibleCandidates) {
                double opinionToTong = worker.getOpinionOf(tongType);
                double opinionToCandidateType = worker.getOpinionOf(candidate.getType());
                double popularity = opinionToTong * opinionToCandidateType;

                boolean shouldSelect = false;
                if (popularity > maxPopularity) {
                    shouldSelect = true;
                } else if (popularity == maxPopularity && selectedCandidate != null) {
                    if (!candidate.isRebellious() && selectedCandidate.isRebellious()) {
                        shouldSelect = true;
                    } else if (candidate.isRebellious() == selectedCandidate.isRebellious()) {
                        if (candidate.getID() < selectedCandidate.getID()) {
                            shouldSelect = true;
                        }
                    }
                }

                if (shouldSelect) {
                    maxPopularity = popularity;
                    selectedCandidate = candidate;
                }
            }

            if (selectedCandidate != null) {
                voteCounts.put(selectedCandidate, voteCounts.get(selectedCandidate) + 1);
            }
        }

        return voteCounts;
    }

    private static FarmAnimal getWinnerOfElections(List<FarmAnimal> eligibleCandidates, Map<FarmAnimal, Integer> voteCounts) {
        FarmAnimal winner = null;
        int maxVotes = -1;
        for (FarmAnimal candidate : eligibleCandidates) {
            int votes = voteCounts.get(candidate);
            if (votes > maxVotes) {
                maxVotes = votes;
                winner = candidate;
            } else if (votes == maxVotes && winner != null) {
                if (candidate.getID() < winner.getID()) {
                    winner = candidate;
                }
            }
        }

        return winner;
    }
}
