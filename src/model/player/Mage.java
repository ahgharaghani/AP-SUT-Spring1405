package model.player;

import model.strategy.AttackStrategy;
import model.weapon.*;

import java.util.LinkedList;
import java.util.List;

public class Mage extends BasePlayer implements Player {
    private static final int baseCost = 110;

    public Mage(String playerId) {
        super(playerId, 20);
    }

    @Override
    public String getType() {
        return "mage";
    }

    @Override
    public int getCostOfWeapon(String weaponType) {
        switch (weaponType) {
            case "wand":
                return Wand.getBaseCost();
            case "staff":
                return Staff.getBaseCost();
            default:
                return -1;
        }
    }

    @Override
    public Weapon forgeWeapon(String weaponType, String weaponId) {
        switch (weaponType) {
            case "wand":
                return new Wand(weaponId, playerId);
            case "staff":
                return new Staff(weaponId, playerId);
            default:
                return null;
        }
    }

    public static int getBaseCost() { return baseCost; }
}
