package model.weapon;

public class Axe extends BaseWeapon implements Weapon {
    private static final int baseCost = 70;

    public Axe(String weaponId, String ownerId) {
        super(weaponId, ownerId);

        this.baseDamage = 35;
    }

    public static int getBaseCost() { return baseCost; }

    @Override
    public String getName() {
        return "axe";
    }
}
