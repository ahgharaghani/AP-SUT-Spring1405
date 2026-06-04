package model.player;

import model.strategy.AttackStrategy;
import model.weapon.*;

import java.util.LinkedList;
import java.util.List;

public class Warrior extends BasePlayer implements Player {
    private static final int baseCost = 100;

    public Warrior(String playerId) {
        super(playerId, 30);
    }

    @Override
    public String getType() {
        return "warrior";
    }

    @Override
    public int getCostOfWeapon(String weaponType) {
        switch (weaponType) {
            case "axe":
                return Axe.getBaseCost();
            case "sword":
                return Sword.getBaseCost();
            default:
                return -1;
        }
    }

    @Override
    public Weapon forgeWeapon(String weaponType, String weaponId) {
        switch (weaponType) {
            case "axe":
                return new Axe(weaponId, playerId);
            case "sword":
                return new Sword(weaponId, playerId);
            default:
                return null;
        }
    }

    public static int getBaseCost() { return baseCost; }
}
