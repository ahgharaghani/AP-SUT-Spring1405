package models;

import models.abilities.Buff;
import models.enums.KnightClass;
import models.enums.KnightName;
import models.enums.Skill;

import java.util.ArrayList;
import java.util.List;

public class Knight {
    private final KnightName name;
    private final KnightClass knightClass;

    private final int maxHp;
    private int hp;

    private final int attack;
    private int effectiveAttack;

    private final int magicAttack;
    private int effectiveMagicAttack;

    private final int defense;
    private int effectiveDefense;

    private final int speed;
    private int effectiveSpeed;

    private final List<Skill> skills;
    private int ap;
    private boolean stunned;
    private List<Buff> buffsApplied;
    private int damageDealt;


    public Knight(KnightName name, KnightClass knightClass,
                  int hp, int attack, int magicAttack, int defense, int speed,
                  List<Skill> skills) {
        this.name = name;
        this.knightClass = knightClass;
        this.maxHp = hp;
        this.hp = hp;
        this.attack = attack;
        this.effectiveAttack = attack;
        this.magicAttack = magicAttack;
        this.effectiveMagicAttack = magicAttack;
        this.defense = defense;
        this.effectiveDefense = defense;
        this.speed = speed;
        this.effectiveSpeed = speed;
        this.skills = skills;
        this.ap = 3;
        this.stunned = false;
        this.buffsApplied = new ArrayList<>();
        this.damageDealt = 0;
    }

    public KnightClass getKnightClass() {
        return this.knightClass;
    }

    public KnightName getName() { return this.name; }

    public String getStringName() {
        return this.name.getName();
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int setHp(int hp) {
        return this.hp = hp;
    }

    public int getMagicAttack() {
        return effectiveMagicAttack;
    }

    public int getAttack() {
        return this.effectiveAttack;
    }

    public int getDefense() {
        return this.effectiveDefense;
    }

    public int getSpeed() {
        return effectiveSpeed;
    }

    public List<Skill> getSkills() {
        return new ArrayList<>(skills);
    }

    public List<Buff> getBuffsApplied() { return new ArrayList<>(buffsApplied); }

    public Double getBuffModifier(Buff.BuffParameter parameter) {
        for (Buff buff : buffsApplied) {
            if (buff.whichParameter() == parameter) {
                return buff.getModifier();
            }
        }
        return null;
    }

    public int getAp() { return ap; }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void addDamageDealt(int amount) {
        this.damageDealt += Math.max(0, amount);
    }

    private void resetParameter(Buff.BuffParameter parameter) {
        switch (parameter) {
            case ATTACK:
                effectiveAttack = attack;
                break;
            case MAGIC_ATTACK:
                effectiveMagicAttack = magicAttack;
                break;
            case DEFENSE:
                effectiveDefense = defense;
                break;
            case SPEED:
                effectiveSpeed = speed;
                break;
        }
    }

    private void applyBuff(Buff buff) {
        switch (buff.whichParameter()) {
            case ATTACK:
                effectiveAttack = (int) (effectiveAttack * (1 + buff.getModifier()));
                break;
            case MAGIC_ATTACK:
                effectiveMagicAttack = (int) (effectiveMagicAttack * (1 + buff.getModifier()));
                break;
            case DEFENSE:
                effectiveDefense = (int) (effectiveDefense * (1 + buff.getModifier()));
                break;
            case SPEED:
                effectiveSpeed = (int) (effectiveSpeed * (1 + buff.getModifier()));
                break;
        }
    }

    private void removeBuffOfSameParameter(Buff.BuffParameter parameter) {
        buffsApplied.removeIf(buff -> buff.whichParameter() == parameter);
        resetParameter(parameter);
    }

    public int receiveDamage(int damage) {
        damage = Math.max(0, damage);
        int actualDamage = Math.min(hp, damage);
        hp -= actualDamage;
        return hp;
    }

    public int heal(double healPercentage) {
        int amount = (int) (maxHp * Math.abs(healPercentage));
        if (healPercentage >= 0) hp = Math.min(maxHp, hp + amount);
        else hp = Math.max(0, hp - amount);
        return amount;
    }

    public int revive(double healModifier) {
        this.ap = 3;
        this.stunned = false;
        return this.hp = (int) (this.maxHp * healModifier);
    }

    public void stun() {
        this.stunned = true;
    }

    public void unstun() {
        this.stunned = false;
    }

    public void cleanBuffs() {
        List<Buff> remained = new ArrayList<>();
        for (Buff buff : buffsApplied) {
            if (buff.getModifier() < 0) resetParameter(buff.whichParameter());
            else remained.add(buff);
        }
        buffsApplied = remained;
    }

    public void addBuff(Buff buff) {
        removeBuffOfSameParameter(buff.whichParameter());
        buffsApplied.add(buff);
        applyBuff(buff);
    }

    public void incrementAp() {
        ap = Math.min(5, ap + 2);
    }

    public void spendAp(int amount) {
        this.ap = Math.max(0, this.ap - amount);
    }

    public boolean isStunned() {
        return stunned;
    }
}
