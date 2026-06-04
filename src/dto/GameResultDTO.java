package dto;

import java.util.List;

public class GameResultDTO {
    private final String winnerUsername;
    private final String loserUsername;
    private final int winnerPoints;
    private final int loserPoints;
    private final List<KnightResultDTO> winnerKnights;
    private final List<KnightResultDTO> loserKnights;

    private final boolean success;
    private final String errorMessage;

    public GameResultDTO(String winnerUsername, String loserUsername,
                         int winnerPoints, int loserPoints,
                         List<KnightResultDTO> winnerKnights,
                         List<KnightResultDTO> loserKnights) {
        this.winnerUsername = winnerUsername;
        this.loserUsername = loserUsername;
        this.winnerPoints = winnerPoints;
        this.loserPoints = loserPoints;
        this.winnerKnights = winnerKnights;
        this.loserKnights = loserKnights;
        this.success = true;
        this.errorMessage = null;
    }

    public GameResultDTO(String errorMessage) {
        this.winnerUsername = null;
        this.loserUsername = null;
        this.winnerPoints = 0;
        this.loserPoints = 0;
        this.winnerKnights = null;
        this.loserKnights = null;
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public String getWinnerUsername() { return winnerUsername; }
    public String getLoserUsername() { return loserUsername; }
    public int getWinnerPoints() { return winnerPoints; }
    public int getLoserPoints() { return loserPoints; }
    public List<KnightResultDTO> getWinnerKnights() { return winnerKnights; }
    public List<KnightResultDTO> getLoserKnights() { return loserKnights; }
    public String getErrorMessage() { return errorMessage; }
    public boolean isSuccess() { return success; }

    public static class KnightResultDTO {
        private final String ownerUsername;
        private final String knightName;
        private final int damageDealt;
        private final int hpRemained;
        private final boolean isAlive;
        private final int points;

        public KnightResultDTO(String ownerUsername, String knightName,
                               int damageDealt, int hpRemained,
                               boolean isAlive, int points) {
            this.ownerUsername = ownerUsername;
            this.knightName = knightName;
            this.damageDealt = damageDealt;
            this.hpRemained = hpRemained;
            this.isAlive = isAlive;
            this.points = points;
        }

        public String getOwnerUsername() { return ownerUsername; }
        public String getKnightName() { return knightName; }
        public int getDamageDealt() { return damageDealt; }
        public int getHpRemained() { return hpRemained; }
        public boolean isAlive() { return isAlive; }
        public int getPoints() { return points; }
    }
}