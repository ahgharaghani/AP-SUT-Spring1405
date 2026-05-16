package models.abilities;

import models.Knight;

public class Heal extends Ability {
    private final double modifier;

    public Heal(String name, boolean isMultipleTarget, double modifier) {
        super(name, isMultipleTarget);
        this.modifier = modifier;
    }

    @Override
    public String execute(Knight target) {
        if (this.name.equals("revive")) {
            if (target.getHp() > 0) return target.getStringName() + " is not dead";
            target.revive(this.modifier);
            return "teammate revived";
        }

        if (target.heal(this.modifier) <= 0) {
            return target.getStringName() + " died!";
        }

        if (this.modifier < 0)
            return target.getStringName() + " lost " + (int)Math.abs(modifier * 100) + "% HP";

        return target.getStringName() + " got healed by " + (int)Math.abs(modifier * 100) + "%";
    }

    @Override
    public String execute(Iterable<Knight> targets) {
        for (Knight t : targets) {
            t.heal(this.modifier);
        }
        return "team got healed by " + Math.abs(modifier * 100) + "%";
    }
}
