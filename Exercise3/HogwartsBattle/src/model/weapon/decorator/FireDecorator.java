package model.weapon.decorator;

import model.weapon.Weapon;

public class FireDecorator extends WeaponDecorator implements Weapon {
    private static final int baseCost = 30;

    public FireDecorator(Weapon source) {
        super(source);
        this.additionalDamage = 15;
    }

    public static int getBaseCost() {
        return baseCost;
    }

    @Override
    public String getName() {
        return wrappee.getName() + " of Fire";
    }
}
