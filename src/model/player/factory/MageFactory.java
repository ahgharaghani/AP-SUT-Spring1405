package model.player.factory;

import model.player.Mage;
import model.player.Player;

public class MageFactory extends PlayerFactory {

    @Override
    public Player createPlayer(String playerId) {
        return new Mage(playerId);
    }

    @Override
    public int getCost() {
        return Mage.getBaseCost();
    }
}