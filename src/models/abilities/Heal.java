package models.abilities;

import models.Knight;

public class Heal extends Ability {
    private final double modifier;

    public Heal(String name, boolean isMultipleTarget, double modifier) {
        super(name, isMultipleTarget);
        this.modifier = modifier;
    }

    @Override
    public String execute(Knight caster, Knight target) {
        int amount = (int)  (Math.abs(modifier * 100));

        if (this.name.equals("revive")) {
            if (target.getHp() > 0) return target.getStringName() + " is not dead";
            target.revive(this.modifier);
            return "teammate revived";
        }

        target.heal(this.modifier);

        if (target.getHp() <= 0) {
            return target.getStringName() + " died!";
        }

        if (this.modifier < 0) {
            return target.getStringName() + " lost " + amount + "% HP";
        }

        return target.getStringName() + " got healed by " + amount + "%";
    }

    @Override
    public String execute(Knight caster, Iterable<Knight> targets) {
        for (Knight target : targets) {
            target.heal(this.modifier);
        }
        return "team got healed by " + (int) (Math.abs(modifier * 100)) + "%";
    }
}