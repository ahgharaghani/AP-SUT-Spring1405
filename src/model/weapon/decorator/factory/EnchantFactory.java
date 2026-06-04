package model.weapon.decorator.factory;

import model.weapon.Weapon;

import java.util.HashMap;
import java.util.Map;

public abstract class EnchantFactory {
    private static final Map<String, EnchantFactory> factories = new HashMap<>();

    static {
        factories.put("fire", new FireEnchantFactory());
        factories.put("poison", new PoisonEnchantFactory());
        factories.put("lightning", new LightningEnchantFactory());
    }

    public static EnchantFactory getFactory(String type) {
        return factories.get(type.toLowerCase());
    }

    public abstract Weapon apply(Weapon weapon);
    public abstract int getCost();
}
