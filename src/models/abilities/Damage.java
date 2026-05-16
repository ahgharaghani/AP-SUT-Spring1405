package models.abilities;

import models.Knight;
import models.enums.KnightClass;

public class Damage extends Ability {
    public Damage(String name, boolean isMultiTarget) {
        super(name, isMultiTarget);
    }

    @Override
    public String execute(Knight caster, Knight target) {
        int damage;

        if (caster.getKnightClass() == KnightClass.MAGE)
            damage = caster.getMagicAttack();
        else if (this.name.equals("heavy strike")) {
            damage = (int) (caster.getAttack() * 1.5) - (int) (target.getDefense() * 0.3);
        } else {
            damage = caster.getAttack() - (int) (target.getDefense() * 0.3);
        }

        if (target.receiveDamageAndGetHP(damage) <= 0) {
            return target.getStringName() + " is dead!";
        }
        return this.name + " dealt " + damage + " receiveDamageAndGetHP to " + target.getStringName();
    }

    @Override
    public String execute(Knight caster, Iterable<Knight> targets) {
        StringBuilder sb = new StringBuilder();

        for (Knight target : targets) {
            int damage;

            if (caster.getKnightClass() == KnightClass.MAGE)
                damage = caster.getMagicAttack();
            else damage = caster.getAttack() - (int) (target.getDefense() * 0.3);

            target.receiveDamage(damage);
            sb.append(this.name).append(" dealt ").append(damage).append(" receiveDamageAndGetHP to ").append(target.getStringName()).append("\n");
            if (target.getHp() == 0) sb.append(target.getStringName()).append(" is dead!\n");
        }

        return sb.toString();
    }
}