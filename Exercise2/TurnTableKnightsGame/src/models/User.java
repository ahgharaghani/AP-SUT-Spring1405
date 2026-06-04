package models;

public class User {
    private final String username;
    private final String password;

    private int gamesPlayed;
    private int gamesWon;
    private double points;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.points = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public double getPoints() {
        return points;
    }

    public void addGamePlayed() {
        this.gamesPlayed++;
    }

    public void addGameWon() {
        this.gamesWon++;
    }

    public void addPoints(double points) {
        this.points += points;
    }
}
