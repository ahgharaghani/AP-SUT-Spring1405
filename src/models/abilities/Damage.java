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
            damage = (int)Math.max (0, Math.floor(caster.getAttack() * 1.5) - Math.floor(target.getDefense() * 0.3));
        } else {
            damage = (int)Math.max(0, caster.getAttack() - Math.floor(target.getDefense() * 0.3));
        }

        damage = Math.max(0, damage);
        target.receiveDamage(damage);
        caster.addDamageDealt(damage);

        String result = this.name + " dealt " + damage + " damage to " + target.getStringName();
        if (target.getHp() == 0) {
            result += "\n" + target.getStringName() + " is dead!";
        }
        return result;
    }

    @Override
    public String execute(Knight caster, Iterable<Knight> targets) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Knight target : targets) {
            if (!first) sb.append("\n");
            first = false;

            int damage;
            if (caster.getKnightClass() == KnightClass.MAGE) {
                damage = caster.getMagicAttack();
            } else {
                damage = caster.getAttack() - (int)Math.floor(target.getDefense() * 0.3);
            }

            damage = Math.max(0, damage);
            target.receiveDamage(damage);
            caster.addDamageDealt(damage);

            sb.append(this.name).append(" dealt ").append(damage)
                    .append(" damage to ").append(target.getStringName());

            if (target.getHp() == 0) {
                sb.append("\n").append(target.getStringName()).append(" is dead!");
            }
        }

        return sb.toString();
    }
}