package model.weapon;

public class Sword extends BaseWeapon implements Weapon{
    private static final int baseCost = 50;

    public Sword(String weaponId, String ownerId) {
        super(weaponId, ownerId);

        this.baseDamage = 25;
    }

    public static int getBaseCost() { return baseCost; }

    @Override
    public String getName() {
        return "sword";
    }
}
