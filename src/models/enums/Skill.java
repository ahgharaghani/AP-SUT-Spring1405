package models.enums;

import models.Knight;
import models.abilities.Buff;
import models.abilities.Damage;
import models.abilities.Heal;
import models.abilities.Stun;

import java.util.List;

public enum Skill {
    SHIELD_BASH("shield bash", 3, TargetSide.ENEMY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            Damage damage = new Damage("shield bash", false);
            Stun stun = new Stun("shield bash", false);

            return damage.execute(caster, target)
                    + "\n" + stun.execute(target);
        }
    },

    FORTIFY("fortify", 1, TargetSide.ALLY, false) {
        @Override
        public String execute(Knight caster, List<Knight> targets) {
            return new Buff("fortify", true, 0.2, Buff.BuffParameter.DEFENSE).execute(targets);
        }
    },

    STRIKE_COMMAND("strike command", 5, TargetSide.ENEMY, false) {
        @Override
        public String execute(Knight caster, List<Knight> targets) {
            return new Damage("strike command", true).execute(caster, targets);
        }
    },

    ARMOR_BREAK("armor break", 2, TargetSide.ENEMY, false) {
        @Override
        public String execute(Knight caster, List<Knight> targets) {
            return new Buff("armor break", true, -0.15, Buff.BuffParameter.DEFENSE).execute(targets);
        }
    },

    RALLY("rally", 4, TargetSide.ALLY, false) {
        @Override
        public String execute(Knight caster, List<Knight> targets) {
            return new Heal("rally", true, 0.2).execute(caster, targets);
        }
    },

    SLASH("slash", 2, TargetSide.ENEMY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            return new Damage("slash", false).execute(caster, target);
        }
    },

    HEAVY_STRIKE("heavy strike", 4, TargetSide.ENEMY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            return new Damage(this.getName(), false).execute(caster, target);
        }
    },

    LIFE_STEAL("life steal", 3, TargetSide.ENEMY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            String result = new Damage(this.getName(), false).execute(caster, target);
//            if (target.getHp() > 0) {
                result += "\n" + new Buff(this.getName(), false, -0.2, Buff.BuffParameter.ATTACK).execute(target);
//            }
            return result;
        }
    },

    BERSERK("berserk", 3, TargetSide.ALLY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            String result = new Heal(this.getName(), false, -0.2).execute(caster, target);
//            if (target.getHp() > 0) {
                result += "\n" + new Buff(this.getName(), false, 0.6, Buff.BuffParameter.ATTACK).execute(target);
//            }
            return result;
        }
    },

    FIREBALL("fireball", 2, TargetSide.ENEMY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            return new Damage(this.getName(), false).execute(caster, target);
        }
    },

    LIGHTNING_STRIKE("lightning strike", 4, TargetSide.ENEMY, false) {
        @Override
        public String execute(Knight caster, List<Knight> targets) {
            return new Damage(this.getName(), true).execute(caster, targets);
        }
    },

    ICE_BLAST("ice blast", 3, TargetSide.ENEMY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            Damage damage  = new Damage(this.getName(), false);
            Buff buff = new Buff(this.getName(), false, -0.2, Buff.BuffParameter.SPEED);
            return damage.execute(caster, target) + "\n" + buff.execute(target);
        }
    },

    ARCANE_SURGE("arcane surge", 3, TargetSide.ALLY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            return new Buff(this.getName(), false, 0.3, Buff.BuffParameter.MAGIC_ATTACK).execute(target);
        }
    },

    SILENCE("silence", 4, TargetSide.ENEMY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            return new Stun(this.getName(), false).execute(target);
        }
    },

    HEAL("heal", 2, TargetSide.ALLY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            return new Heal(this.getName(), false, 0.4).execute(caster, target);
        }
    },

    GROUP_HEAL("group heal", 5, TargetSide.ALLY, false) {
        @Override
        public String execute(Knight caster, List<Knight> targets) {
            return new Heal(this.getName(), true, 0.2).execute(caster, targets);
        }
    },

    REVIVE("revive", 4, TargetSide.ALLY, true) {
        @Override
        public String execute(Knight caster, Knight target) {
            return new Heal(this.getName(), false, 0.1).execute(caster, target);
        }
    },

    CLEANSE("cleanse", 3, TargetSide.ALLY, false) {
        @Override
        public String execute(Knight caster, List<Knight> targets) {
            return new Buff(this.getName(), true, 0, null).execute(targets);
        }
    };

    public enum TargetSide {
        ALLY, ENEMY
    }

    private final String name;
    private final int apReq;
    private final TargetSide targetSide;
    private final boolean needsTarget;

    Skill(String name, int apReq, TargetSide targetSide, boolean needsTarget) {
        this.name = name;
        this.apReq = apReq;
        this.targetSide = targetSide;
        this.needsTarget = needsTarget;
    }

    public String execute(Knight caster, Knight target) {
        throw new UnsupportedOperationException();
    }

    public String execute(Knight caster, List<Knight> targets) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return name;
    }

    public int getApReq() {
        return apReq;
    }

    public TargetSide getTargetSide() {
        return targetSide;
    }

    public boolean needsTarget() {
        return needsTarget;
    }

    public static Skill fromName(String skillName) {
        for (Skill skill : values()) {
            if (skill.getName().equalsIgnoreCase(skillName.trim())) {
                return skill;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}