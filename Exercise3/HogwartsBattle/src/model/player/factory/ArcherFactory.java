package model.player.factory;

import model.player.Archer;
import model.player.Player;

public class ArcherFactory extends PlayerFactory {

    @Override
    public Player createPlayer(String playerId) {
        return new Archer(playerId);
    }

    @Override
    public int getCost() {
        return Archer.getBaseCost();
    }
}