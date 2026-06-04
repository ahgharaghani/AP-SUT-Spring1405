package model.weapon;

public class Bow extends BaseWeapon implements Weapon{
    private static final int baseCost = 60;

    public Bow(String weaponId, String ownerId) {
        super(weaponId, ownerId);

        this.baseDamage = 20;
    }

    public static int getBaseCost() { return baseCost; }

    @Override
    public String getName() {
        return "bow";
    }
}
