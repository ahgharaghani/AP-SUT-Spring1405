package model.player.factory;

import model.player.Player;
import model.player.Warrior;

public class WarriorFactory extends PlayerFactory {

    @Override
    public Player createPlayer(String playerId) {
        return new Warrior(playerId);
    }

    @Override
    public int getCost() {
        return Warrior.getBaseCost();
    }
}