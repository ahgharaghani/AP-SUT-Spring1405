package model.weapon.decorator;

import model.weapon.Weapon;

public class LightningDecorator extends WeaponDecorator implements Weapon {
    private static final int baseCost = 40;

    public LightningDecorator(Weapon source) {
        super(source);
        this.additionalDamage = 20;
    }

    public static int getBaseCost() {
        return baseCost;
    }

    @Override
    public String getName() {
        return wrappee.getName() + " of Lightning";
    }
}