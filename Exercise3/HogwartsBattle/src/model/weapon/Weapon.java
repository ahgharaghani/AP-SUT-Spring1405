package model.weapon;

public interface Weapon {
    String getWeaponId();
    String getOwnerId();
    int getLevel();
    void upgradeLevel();
    double getMultiplier();
    String getName();
    int getBaseDamage();
    int getTotalDamage();


}
