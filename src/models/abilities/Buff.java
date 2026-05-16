package models.abilities;

import models.Knight;

public class Buff extends Ability {

    public enum BuffParameter {
        ATTACK, DEFENSE, MAGIC_ATTACK, SPEED
    }

    private double modifier;
    private BuffParameter parameter;

    public Buff(String name, boolean isMultipleTarget, double modifier, BuffParameter parameter) {
        super(name, isMultipleTarget);
        this.modifier = modifier;
        this.parameter = parameter;
    }

    private String getParameterName() {
        switch (parameter) {
            case ATTACK:
                return "attack";
            case DEFENSE:
                return "defense";
            case MAGIC_ATTACK:
                return "magic attack";
            case SPEED:
                return "speed";
            default:
                return "";
        }
    }

    @Override
    public String execute(Knight target) {
        if (this.name.equals("cleanse")) {
            target.cleanBuffs();
            return "debuffs removed";
        }
        target.addBuff(this);
        int amount = (int) Math.abs(modifier * 100);

        if (modifier > 0) {
            return target.getStringName() + "'s " + getParameterName() + " buffed by " + amount + "%";
        }
        return target.getStringName() + "'s " + getParameterName() + " nerfed by " + amount + "%";
    }

    @Override
    public String execute(Iterable<Knight> targets) {
        for (Knight target : targets) {
            if (this.name.equals("cleanse")) {
                target.cleanBuffs();
            } else {
                target.addBuff(this);
            }
        }
        if (this.name.equals("cleanse")) {
            return "debuffs removed";
        }

        int amount = (int) Math.abs(modifier * 100);

        if (modifier > 0) {
            return "team's " + getParameterName() + " buffed by " + amount + "%";
        }
        return "enemy's " + getParameterName() + " nerfed by " + amount + "%";
    }

    public double getModifier() {
        return this.modifier;
    }

    public BuffParameter whichParameter() {
        return this.parameter;
    }
}
