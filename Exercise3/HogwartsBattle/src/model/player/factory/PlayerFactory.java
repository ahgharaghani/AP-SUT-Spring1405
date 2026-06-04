package model.player.factory;

import model.player.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class PlayerFactory {

    private static final Map<String, PlayerFactory> factories =
            new HashMap<>();

    static {
        factories.put("warrior", new WarriorFactory());
        factories.put("mage", new MageFactory());
        factories.put("archer", new ArcherFactory());
    }

    public static PlayerFactory getFactory(String type) {
        return factories.get(type.toLowerCase());
    }

    public abstract Player createPlayer(String playerId);

    public abstract int getCost();
}
