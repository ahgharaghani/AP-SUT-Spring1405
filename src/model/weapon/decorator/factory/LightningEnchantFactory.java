package model.weapon.decorator.factory;

import model.weapon.Weapon;
import model.weapon.decorator.LightningDecorator;

public class LightningEnchantFactory extends EnchantFactory {
    @Override
    public Weapon apply(Weapon weapon) { return new LightningDecorator(weapon); }

    @Override
    public int getCost() { return LightningDecorator.getBaseCost(); }
}
