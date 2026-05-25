package view;

import controller.AppController;
import controller.GameController;
import dto.AnimalDetailsDTO;
import dto.GovernorHistoryDTO;
import dto.RebellionResultDTO;
import dto.WorkResult;
import dto.WorkResult.DeathOutcome;
import model.App;
import model.command.GameCommand;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GameView {
    public static void handleCommand(Scanner scanner) {
        String currentState = GameController.getCurrentState();

        if (currentState.equals("normal turn")) {
            handleTurn(scanner);
        } else if (currentState.equals("system vote")) {
            handleSystemVote(scanner);
        } else if (currentState.equals("governor vote")) {
            handleGovernorVote(scanner);
        }
    }

    private static void handleTurn(Scanner scanner) {
        printWhoPlays();
        String query = scanner.nextLine();

        if (GameCommand.SHOW_ANIMAL_DETAILS.matches(query)) {
            if (shouldChooseSheriff()) return;
            List<AnimalDetailsDTO> results = GameController.getAnimalDetails();
            String parsedResult = parseAnimalsDetails(results);

            System.out.print(parsedResult);
        } else if (GameCommand.WORK.matches(query)) {
            if (shouldChooseSheriff()) return;
            String hours = GameCommand.WORK.getParameter("hours");
            WorkResult result = GameController.work(hours);

            if (result.outcome == WorkResult.DeathOutcome.SUCCESS) {
                System.out.println("The future will be held on your shoulders!");
                handleEndOfDay();
                return;
            } else if (result.outcome == WorkResult.DeathOutcome.ERROR) {
                System.out.println(result.errorMessage);
                handleEndOfDay();
                return;
            }

            System.out.println("The future will be held on your shoulders!");
            handleDeath(result);
            handleEndOfDay();
        } else if (GameCommand.SPREAD_RUMOR.matches(query)) {
            if (shouldChooseSheriff()) return;
            String id = GameCommand.SPREAD_RUMOR.getParameter("id");
            String type = GameCommand.SPREAD_RUMOR.getParameter("type");

            String result = GameController.spreadRumor(id, type);
            System.out.println(result);
            handleEndOfDay();
        } else if (GameCommand.CHOOSE_SHERIFF.matches(query)) {
            String id =  GameCommand.CHOOSE_SHERIFF.getParameter("id");

            String result = GameController.chooseSheriff(id);
            System.out.println(result);
            handleEndOfDay();
        } else if (GameCommand.SET_TRADE_RATE.matches(query)) {
            String percent = GameCommand.SET_TRADE_RATE.getParameter("percent");

            String result = GameController.setTradeRate(percent);
            System.out.println(result);
            handleEndOfDay();
        } else if (GameCommand.CHANGE_RULE.matches(query)) {
            String oldRule = GameCommand.CHANGE_RULE.getParameter("oldRule");
            String newRule = GameCommand.CHANGE_RULE.getParameter("newRule");

            String result = GameController.changeRule(newRule, oldRule);
            System.out.println(result);
            handleEndOfDay();
        } else if (GameCommand.SHOW_GOVERNORS_HISTORY.matches(query)) {
//            if (shouldChooseSheriff()) return;
            List<GovernorHistoryDTO> results = GameController.getGovernorsHistory();
            if (results == null) {
                System.out.println("No governors found.");
                return;
            }
            String parsedResults = parseGovernorsHistory(results);

            System.out.println(parsedResults);
        } else if (GameCommand.REBEL.matches(query)) {
            if (shouldChooseSheriff()) return;
            String candidate = GameCommand.REBEL.getParameter("candidate");
            String tong = GameCommand.REBEL.getParameter("tong");

            RebellionResultDTO result = GameController.rebel(candidate, tong);

            if (result.isFailure()) {
                handleRebellionFailure(result);
            } else handleRebellionSuccess(result);

            handleEndOfDay();
        } else if (GameCommand.BACK_TO_MAIN_MENU.matches(query)) {
            GameController.backToMainMenu();
        } else if (GameCommand.SHOW_RULES.matches(query)) {
            List<String> rules = GameController.getRules();
            for (int i = 0; i < rules.size(); i++) {
                System.out.println((i + 1) + ". " + rules.get(i));
            }
        }
        else System.out.println("Invalid command");
    }

    private static void handleEndOfDay() {
        int day = GameController.checkIfDayOver();
        if (day > 0) {
            System.out.println("=== Day " + day + " has ended ===");
        } if (day > 1) System.out.println();
    }

    private static void handleSystemVote(Scanner scanner) {
        String currentState = GameController.getCurrentState();
        System.out.println("Voting for the Political System Begins\n");

        while (currentState.equals("system vote")) {
            printWhoPlays();
            String query = scanner.nextLine();

            if (GameCommand.VOTE.matches(query)) {
                String tong = GameCommand.VOTE.getParameter("tong");

                String result = GameController.castVote(tong);
                System.out.println(result);

            } else System.out.println("Only vote command is allowed during political system voting.");

            currentState = GameController.getCurrentState();
        }

        System.out.println("Political system selected: " + GameController.getSystem().toLowerCase());
    }

    private static void handleGovernorVote(Scanner scanner) {
        String currentState = GameController.getCurrentState();
        String system = GameController.getSystem();

        if (system.equalsIgnoreCase("democracy")) {
            while (currentState.equals("governor vote")) {
                printWhoPlays();
                String query = scanner.nextLine();

                if (GameCommand.VOTE_GOVERNOR.matches(query)) {
                    String id = GameCommand.VOTE_GOVERNOR.getParameter("id");
                    String result = GameController.castGovernorVote(id);
                    System.out.println(result);
                } else {
                    System.out.println("Only governor vote command is allowed during governor selection.");
                }
                currentState = GameController.getCurrentState();
            }
        } else {
            GameController.finalizeGovernor();
        }

        System.out.println("The first governor is: " + GameController.getGovernorName());
    }

    private static void handleDeath(WorkResult result) {
        switch (result.outcome) {
            case DIED_NORMAL:
                System.out.println("You have died.");
                System.out.println("Farewell, " + result.deadName + ".");
                System.out.println("ID: " + result.deadId);
                break;
            case DIED_GOVERNOR:
                System.out.println("The farm bids you farewell " + result.deadName + ", " + result.deadId);
                System.out.println("Your rule has ended.");
                System.out.println("All animals are equal...");
                System.out.println("But " + result.newGovernorName + " (ID: " + result.newGovernorId + ") is now more equal than others.");
                break;
            case GAME_OVER:
                System.out.println("You have died.");
                System.out.println("Farewell, " + result.deadName + ".");
                System.out.println("ID: " + result.deadId);
                System.out.println("Not enough animals to continue the game.");
                System.out.println("Game over.");
                break;
        }
    }

    public static void printWhoPlays() {
        String currentPlayer = GameController.whoIsPlaying();
        System.out.println(currentPlayer);
        System.out.flush();
    }

    private static String parseAnimalsDetails(List<AnimalDetailsDTO> dtos) {
        StringBuilder sb = new StringBuilder();
        for (AnimalDetailsDTO details : dtos) {
            Map<String, Double> politicalOpinions = details.getPoliticalOpinion();
            Map<String, Double> specieOpinions = details.getAnimalTypeOpinion();
            sb.append("------------------\n");
            sb.append("ID = ").append(details.getID()).append("\n");
            sb.append("Name = ").append(details.getName()).append("\n");
            sb.append("Type = ").append(details.getType()).append("\n");
            sb.append("Role = ").append(details.getRole()).append("\n");
            sb.append("\n");
            sb.append("Fullness = ").append(String.format("%.1f", details.getFullness())).append("\n");
            sb.append("\n");
            sb.append("Rebellious = ").append(details.isRebellious()).append("\n");
            sb.append("Liar = ").append(details.isLiar()).append("\n");
            sb.append("Corrupt = ").append(details.isCorrupt()).append("\n");
            sb.append("Lazy = ").append(details.isLazy()).append("\n");
            sb.append("\n");
            sb.append("Political Opinions:\n");
            sb.append("democracy = ").append(String.format("%.1f", politicalOpinions.get("democracy"))).append("\n");
            sb.append("communism = ").append(String.format("%.1f", politicalOpinions.get("communism"))).append("\n");
            sb.append("dictatorship = ").append(String.format("%.1f", politicalOpinions.get("dictatorship"))).append("\n");
            sb.append("\n");
            sb.append("Type Opinions:\n");
            sb.append("dog = ").append(String.format("%.1f", specieOpinions.get("dog"))).append("\n");
            sb.append("horse = ").append(String.format("%.1f", specieOpinions.get("horse"))).append("\n");
            sb.append("pig = ").append(String.format("%.1f", specieOpinions.get("pig"))).append("\n");
            sb.append("sheep = ").append(String.format("%.1f", specieOpinions.get("sheep"))).append("\n");
            sb.append("cow = ").append(String.format("%.1f", specieOpinions.get("cow"))).append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }

    private static String parseGovernorsHistory(List<GovernorHistoryDTO> dtos) {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------\n");
        sb.append("Governments History\n");
        sb.append("------------------\n");
        for (GovernorHistoryDTO dto : dtos) {
            sb.append("ID=").append(dto.id)
                    .append(", Name=").append(dto.name)
                    .append(", Party=").append(dto.ideology)
                    .append(", Started Day=").append(dto.day).append("\n");
        }

        return sb.toString();
    }

    private static boolean shouldChooseSheriff() {
        if (GameController.shouldChooseSheriff()) {
            System.out.println("You have to choose a sheriff today.");
            return true;
        }
        return false;
    }

    private static void handleRebellionFailure(RebellionResultDTO result) {
        switch (result.getStatus()) {
            case NOT_A_WORKER:
                System.out.println("Only workers can start a rebellion.");
                break;
            case CANDIDATE_NOT_FOUND:
                System.out.println("There is no animal with the suggested name.");
                break;
            case INVALID_TONG:
                System.out.println("The suggested tong doesn't exist.");
                break;
            case CONDITIONS_NOT_MET:
                System.out.println("Rebellion cannot be started now.");
                break;
            case ONLY_GOVERNOR_SUGGESTED:
                System.out.println("You cannot rebel in favor of the current governor.");
                break;
            case NO_VALID_CANDIDATE:
                System.out.println("No valid candidate found for rebellion.");
                break;
            case NOT_ENOUGH_SUPPORT:
                System.out.println("[ REBELLION FAILED ]");
                System.out.println("Not enough workers support the rebellion.");
                break;
        }
    }

    private static void handleRebellionSuccess(RebellionResultDTO result) {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("    REBELLION IN PROGRESS\n");
        sb.append("================================\n");
        sb.append("Candidates:\n");
        for (RebellionResultDTO.RebelCandidateDTO c : result.getCandidates()) {
            sb.append(String.format("  • ID: %d  | %s          | Governorships: %d\n",
                    c.getId(), c.getName(), c.getGovernorships()));
        }
        sb.append("\n");
        sb.append("--------------------------------\n");
        sb.append("         VOTING RESULTS\n");
        sb.append("--------------------------------\n");
        for (RebellionResultDTO.RebelVoteResultDTO v : result.getVoteResults()) {
            sb.append(String.format("  %s (ID: %d) ......... %d votes\n",
                    v.getName(), v.getId(), v.getVotes()));
        }
        sb.append("\n");
        sb.append("================================\n");
        sb.append("   REBELLION SUCCEEDED!\n");
        sb.append("================================\n");
        sb.append(String.format("  New Governor: %s (ID: %d)\n",
                result.getNewGovernorName(), result.getNewGovernorId()));
        sb.append("  New Political System: ").append(result.getNewPoliticalSystem()).append("\n");
        sb.append("================================");

        System.out.println(sb);
    }
}
