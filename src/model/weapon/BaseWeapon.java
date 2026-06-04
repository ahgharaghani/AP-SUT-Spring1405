package model.weapon;

public abstract class BaseWeapon implements Weapon {
    protected int baseDamage;

    protected final String weaponId;
    protected final String ownerId; // no ref to Player to avoid circular refs
    protected int level;

    public BaseWeapon(String weaponId, String ownerId) {
        this.weaponId = weaponId;
        this.ownerId = ownerId;
        this.level = 1;
    }

    @Override
    public int getBaseDamage() { return baseDamage; }

    @Override
    public int getTotalDamage() { return baseDamage; }

    @Override
    public String getWeaponId() {
        return weaponId;
    }

    @Override
    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void upgradeLevel() {
        level += 1;
        if (level > 5) level = 5;
    }

    @Override
    public double getMultiplier() {
        switch (level) {
            case 1:
                return 1.0;
            case 2:
                return 1.2;
            case 3:
                return 1.5;
            case 4:
                return 2.0;
            case 5:
                return 3.0;
            default:
                return 0;
        }
    }
}
