package models;

import factories.KnightFactory;
import models.enums.KnightName;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    public enum GameState {
        PICKING, ONGOING, CONCLUDED;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public enum Turn {
        KNIGHT_1_1(1), KNIGHT_1_2(2), KNIGHT_2_1(1), KNIGHT_2_2(2);

        private final int index;

        Turn(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public Turn next() {
            Turn[] values = values();
            return values[(ordinal() + 1) % values.length];
        }
    }

    private User firstPlayer;
    private User secondPlayer;
    private Turn whoseTurn;
    private GameState state;
    private List<Knight> firstPlayerKnights;
    private List<Knight> secondPlayerKnights;

    public GameSession(User firstPlayer, User secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.whoseTurn = Turn.KNIGHT_1_1;
        this.state = GameState.PICKING;
        this.firstPlayerKnights = new ArrayList<>();
        this.secondPlayerKnights = new ArrayList<>();
    }

    public Turn getWhoseTurn() { return this.whoseTurn; }

    private User getFirstPlayer() { return this.firstPlayer; }
    private User getSecondPlayer() { return this.secondPlayer; }
    private Knight getFirstPlayerKnight() { return this.firstPlayerKnights.get(whoseTurn.ordinal() % 2); }
    private Knight getSecondPlayerKnight() { return this.secondPlayerKnights.get(whoseTurn.ordinal() % 2); }

    public User getCurrentPlayer() {
        if (whoseTurn.ordinal() < 2) return getFirstPlayer();
        return getSecondPlayer();
    }

    public Knight getCurrentPlayerKnight() {
        if (whoseTurn.ordinal() < 2) return getFirstPlayerKnight();
        return getSecondPlayerKnight();
    }

    public Knight getTeammate() {
        if (whoseTurn.ordinal() < 2) return firstPlayerKnights.get((whoseTurn.ordinal() + 1) % 2);
        return secondPlayerKnights.get((whoseTurn.ordinal() + 1) % 2);
    }

    public List<Knight> getAllyKnights() {
        if (whoseTurn.ordinal() < 2) return firstPlayerKnights;
        return secondPlayerKnights;
    }

    public User getEnemy() {
        if (whoseTurn.ordinal() < 2) return secondPlayer;
        return firstPlayer;
    }

    public List<Knight> getEnemyKnights() {
        if (whoseTurn.ordinal() < 2) return secondPlayerKnights;
        return firstPlayerKnights;
    }

    public List<Knight> getAliveKnights(List<Knight> knights) {
        List<Knight> alive = new ArrayList<>();
        for (Knight k : knights) {
            if (k.getHp() > 0) {
                alive.add(k);
            }
        }
        return alive;
    }

    private boolean isKnightAlreadySelected(KnightName knightName, List<Knight> knights) {
        List<Knight> toBeChecked = new ArrayList<>(knights);
        for (Knight k : toBeChecked) {
            if (k.getName() == knightName) return true;
        }
        return false;
    }

    public boolean isFirstPlayerKnightAlreadySelected(KnightName knightName) {
        return isKnightAlreadySelected(knightName, firstPlayerKnights);
    }

    public boolean isSecondPlayerKnightAlreadySelected(KnightName knightName) {
        return isKnightAlreadySelected(knightName, secondPlayerKnights);
    }

    public void addKnightToFirstPlayer(KnightName knightName) {
        this.firstPlayerKnights.add(KnightFactory.getKnight(knightName));
    }

    public void addKnightToSecondPlayer(KnightName knightName) {
        this.secondPlayerKnights.add(KnightFactory.getKnight(knightName));
    }

    public String whoMustChooseKnight() {
        if (state != GameState.PICKING) return null;
        if (firstPlayerKnights.size() < 2) return firstPlayer.getUsername();
        else return secondPlayer.getUsername();
    }

    public int usernameIsWhichPlayer(String username) {
        if (firstPlayer.getUsername().equals(username)) return 1;
        else return 2;
    }

    public boolean shallWeBegin() {
        return firstPlayerKnights.size() == 2 && secondPlayerKnights.size() == 2;
    }

    public void setAsOngoing() {
        state = GameState.ONGOING;
    }

    public void setAsConcluded() {
        state = GameState.CONCLUDED;
    }

    public void incrementTurn() {
        while (true) {
            whoseTurn = whoseTurn.next();
            Knight currentKnight = getCurrentPlayerKnight();
            if (currentKnight.getHp() <= 0) continue;
            if (currentKnight.isStunned()) {
                currentKnight.unstun();
                continue;
            }
            currentKnight.incrementAp();
            break;
        }
    }

    public boolean isUserInGame(String username) {
        return firstPlayer.getUsername().equals(username) || secondPlayer.getUsername().equals(username);
    }

    public List<Knight> getKnightsOfUser(String username) {
        if (firstPlayer.getUsername().equals(username)) return firstPlayerKnights;
        else if (secondPlayer.getUsername().equals(username)) return secondPlayerKnights;
        return null;
    }

    public int anyWinner() {
        boolean firstTeamAsWinner = true, secondTeamAsWinner = true;
        for (Knight k : firstPlayerKnights) {
            if (k.getHp() > 0) {
                secondTeamAsWinner = false;
                break;
            }
        }
        for (Knight k : secondPlayerKnights) {
            if (k.getHp() > 0) {
                firstTeamAsWinner = false;
                break;
            }
        }
        if (firstTeamAsWinner) return 1;
        else if (secondTeamAsWinner) return 2;
        else return 0;
    }
}
