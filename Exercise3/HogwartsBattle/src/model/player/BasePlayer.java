package model.player;

import model.strategy.AttackStrategy;
import model.strategy.Balanced;
import model.weapon.Weapon;

import java.util.LinkedList;
import java.util.List;

public abstract class BasePlayer implements Player {
    protected final String playerId;
    protected List<Weapon> weapons;
    protected AttackStrategy attackStrategy;
    protected final int basePoints;

    public BasePlayer(String playerId, int basePoints) {
        this.playerId = playerId;
        this.weapons = new LinkedList<>();
        this.attackStrategy = new Balanced();
        this.basePoints = basePoints;
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }

    @Override
    public int getBaseScore() {
        return basePoints;
    }

    @Override
    public void addWeapon(Weapon newWeapon) {
        weapons.add(newWeapon);
    }

    @Override
    public void replaceWeapon(Weapon oldWeapon, Weapon newWeapon) {
        for (int i = 0; i < weapons.size(); i++) {
            if (weapons.get(i) == oldWeapon) {
                weapons.set(i, newWeapon);
                return;
            }
        }
    }

    @Override
    public List<Weapon> getWeapons() {
        return weapons;
    }

    @Override
    public void setAttackStrategy(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    @Override
    public String getStrategyName() {
        return attackStrategy.getName();
    }

    @Override
    public double attack(Weapon weapon) {
        double finalScore = (getBaseScore() * attackStrategy.getMultiplier())
                + (weapon.getTotalDamage() * weapon.getMultiplier());
        return finalScore;
    }
}
