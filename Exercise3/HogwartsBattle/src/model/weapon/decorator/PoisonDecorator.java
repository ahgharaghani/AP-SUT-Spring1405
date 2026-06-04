package model.weapon.decorator;

import model.weapon.Weapon;

public class PoisonDecorator extends WeaponDecorator implements Weapon {
    private static final int baseCost = 10;

    public PoisonDecorator(Weapon source) {
        super(source);
        this.additionalDamage = 5;
    }

    public static int getBaseCost() {
        return baseCost;
    }

    @Override
    public String getName() {
        return wrappee.getName() + " of Poison";
    }
}
