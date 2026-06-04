package model.weapon;

public class Dagger extends BaseWeapon implements Weapon {
    private static final int baseCost = 40;

    public Dagger(String weaponId, String ownerId) {
        super(weaponId, ownerId);

        this.baseDamage = 15;
    }

    public static int getBaseCost() { return baseCost; }

    @Override
    public String getName() {
        return "dagger";
    }
}
