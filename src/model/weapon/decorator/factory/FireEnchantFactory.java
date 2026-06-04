package model.weapon.decorator.factory;

import model.weapon.Weapon;
import model.weapon.decorator.FireDecorator;

public class FireEnchantFactory extends EnchantFactory {
    @Override
    public Weapon apply(Weapon weapon) { return new FireDecorator(weapon); }

    @Override
    public int getCost() { return FireDecorator.getBaseCost(); }
}