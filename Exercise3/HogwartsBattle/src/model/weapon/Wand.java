package model.weapon;

public class Wand extends BaseWeapon implements Weapon {
    private static final int baseCost = 55;

    public Wand(String weaponId, String ownerId) {
        super(weaponId, ownerId);

        this.baseDamage = 18;
    }

    public static int getBaseCost() { return baseCost; }

    @Override
    public String getName() {
        return "wand";
    }
}
