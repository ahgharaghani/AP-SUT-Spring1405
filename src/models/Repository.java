package models;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static List<User> playersRepo = new ArrayList<>();

    public static void addPlayer(User newUser) {
        playersRepo.add(newUser);
    }

    public static List<User> getAllPlayers() {
        return new ArrayList<>(playersRepo);
    }

    public static User getPlayerByUsername(String username) {
        return playersRepo.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
