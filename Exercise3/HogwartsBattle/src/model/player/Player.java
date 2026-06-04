package model.player;

import model.strategy.AttackStrategy;
import model.weapon.Weapon;

import java.util.List;

public interface Player {
    String getPlayerId();
    String getType();
    int getBaseScore();

    int getCostOfWeapon(String weaponType);
    Weapon forgeWeapon(String weaponType, String weaponId);
    void addWeapon(Weapon newWeapon);
    void replaceWeapon(Weapon oldWeapon, Weapon newWeapon);
    List<Weapon> getWeapons();
    void setAttackStrategy(AttackStrategy strategy);
    String getStrategyName();

    double attack(Weapon weapon);
}
