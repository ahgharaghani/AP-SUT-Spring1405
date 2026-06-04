package model.weapon.decorator.factory;

import model.weapon.Weapon;
import model.weapon.decorator.PoisonDecorator;

public class PoisonEnchantFactory extends EnchantFactory {
    @Override
    public Weapon apply(Weapon weapon) { return new PoisonDecorator(weapon); }

    @Override
    public int getCost() { return PoisonDecorator.getBaseCost(); }
}