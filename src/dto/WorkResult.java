package dto;

public class WorkResult {
    public enum DeathOutcome {
        SUCCESS,
        ERROR,
        DIED_NORMAL,
        DIED_GOVERNOR,
        GAME_OVER,
    }


    public final DeathOutcome outcome;
    public final String errorMessage;
    public final String deadName;
    public final int    deadId;

    public final String newGovernorName;
    public final int    newGovernorId;

    private WorkResult(DeathOutcome outcome, String errorMessage,
                       String deadName, int deadId,
                       String newGovernorName, int newGovernorId) {
        this.outcome  = outcome;
        this.errorMessage = errorMessage;
        this.deadName = deadName;
        this.deadId = deadId;
        this.newGovernorName = newGovernorName;
        this.newGovernorId = newGovernorId;
    }

    public static WorkResult success() {
        return new WorkResult(DeathOutcome.SUCCESS, null, null, -1, null, -1);
    }

    public static WorkResult error(String message) {
        return new WorkResult(DeathOutcome.ERROR, message, null, -1, null, -1);
    }

    public static WorkResult diedNormal(String deadName, int deadId) {
        return new WorkResult(DeathOutcome.DIED_NORMAL, null, deadName, deadId, null, -1);
    }

    public static WorkResult diedGovernor(String deadName, int deadId,
                                          String newGovName, int newGovId) {
        return new WorkResult(DeathOutcome.DIED_GOVERNOR, null,
                deadName, deadId, newGovName, newGovId);
    }
    
    public static WorkResult gameOver(String deadName, int deadId) {
        return new WorkResult(DeathOutcome.GAME_OVER, null, deadName, deadId, null, -1);
    }
}