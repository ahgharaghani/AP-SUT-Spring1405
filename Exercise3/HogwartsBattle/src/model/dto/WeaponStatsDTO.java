package model.dto;

import model.weapon.Weapon;

public class WeaponStatsDTO {
    private final String weaponId;
    private final String name;
    private final int level;
    private final int baseDamage;
    private final int totalDamage;
    private final String ownerId;

    public WeaponStatsDTO(Weapon weapon) {
        this.weaponId = weapon.getWeaponId();
        this.name = weapon.getName();
        this.level = weapon.getLevel();
        this.baseDamage = weapon.getBaseDamage();
        this.totalDamage = weapon.getTotalDamage();
        this.ownerId = weapon.getOwnerId();
    }

    public String getWeaponId() {
        return weaponId;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getTotalDamage() {
        return totalDamage;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
