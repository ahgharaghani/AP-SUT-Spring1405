package model.weapon;

public class Staff extends BaseWeapon implements Weapon{
    private static final int baseCost = 80;

    public Staff(String weaponId, String ownerId) {
        super(weaponId, ownerId);

        this.baseDamage = 22;
    }

    public static int getBaseCost() { return baseCost; }

    @Override
    public String getName() {
        return "staff";
    }
}
