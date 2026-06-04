package model.weapon.decorator;

import model.weapon.Weapon;

public abstract class WeaponDecorator implements Weapon {
    protected Weapon wrappee;
    protected int additionalDamage;

    public WeaponDecorator(Weapon source) {
        wrappee = source;
    }

    @Override
    public int getBaseDamage() { return wrappee.getBaseDamage(); }

    @Override
    public int getTotalDamage() {
        return wrappee.getTotalDamage() + additionalDamage;
    }

    @Override
    public String getWeaponId() {
        return wrappee.getWeaponId();
    }

    @Override
    public String getOwnerId() {
        return wrappee.getOwnerId();
    }

    @Override
    public int getLevel() {
        return wrappee.getLevel();
    }

    @Override
    public void upgradeLevel() {
        wrappee.upgradeLevel();
    }

    @Override
    public double getMultiplier() {
        return wrappee.getMultiplier();
    }
}
