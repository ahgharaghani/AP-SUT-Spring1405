package model.player;

import model.strategy.Balanced;
import model.strategy.AttackStrategy;
import model.weapon.Bow;
import model.weapon.Dagger;
import model.weapon.Weapon;

import java.util.LinkedList;
import java.util.List;

public class Archer extends BasePlayer implements Player {
    private static final int baseCost = 90;


    public Archer(String playerId) {
        super(playerId, 25);
    }

    @Override
    public String getType() {
        return "archer";
    }

    @Override
    public int getCostOfWeapon(String weaponType) {
        switch (weaponType) {
            case "dagger":
                return Dagger.getBaseCost();
            case "bow":
                return Bow.getBaseCost();
            default:
                return -1;
        }
    }

    @Override
    public Weapon forgeWeapon(String weaponType, String weaponId) {
        switch (weaponType) {
            case "dagger":
                return new Dagger(weaponId, playerId);
            case "bow":
                return new Bow(weaponId, playerId);
            default:
                return null;
        }
    }

    public static int getBaseCost() { return baseCost; }
}
