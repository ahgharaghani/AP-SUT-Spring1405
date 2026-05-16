package factories;

import models.Knight;
import models.enums.KnightClass;
import models.enums.KnightName;
import models.enums.Skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightFactory {
    public static Knight getKnight(KnightName name) {
        if (name == KnightName.ARTHUR) {
            List<Skill> skills = new ArrayList<>(Arrays.asList(
                    Skill.SHIELD_BASH, Skill.FORTIFY, Skill.ARMOR_BREAK, Skill.RALLY
            ));

            return new Knight(
                    KnightName.ARTHUR, KnightClass.COMMANDER,
                    200, 50, 15, 60, 30,
                    skills
            );
        } else if (name == KnightName.MORDRED) {
            List<Skill> skills = new ArrayList<>(Arrays.asList(
                    Skill.SHIELD_BASH, Skill.STRIKE_COMMAND, Skill.ARMOR_BREAK, Skill.RALLY
            ));

            return new Knight(
                    KnightName.MORDRED, KnightClass.COMMANDER,
                    180, 40, 20, 50, 35,
                    skills
            );
        } else if (name == KnightName.LANCELOT) {
            List<Skill> skills = new ArrayList<>(Arrays.asList(
                    Skill.SLASH, Skill.HEAVY_STRIKE, Skill.LIFE_STEAL, Skill.BERSERK
            ));

            return new Knight(
                    KnightName.LANCELOT, KnightClass.WARRIOR,
                    170, 60, 5, 70, 50,
                    skills
            );
        } else if (name == KnightName.GALAHAD) {
            List<Skill> skills = new ArrayList<>(Arrays.asList(
                    Skill.HEAL, Skill.GROUP_HEAL, Skill.REVIVE, Skill.CLEANSE
            ));

            return new Knight(
                    KnightName.GALAHAD, KnightClass.HEALER,
                    150, 20, 25, 40, 70,
                    skills
            );
        } else if (name == KnightName.MORGAN) {
            List<Skill> skills = new ArrayList<>(Arrays.asList(
                    Skill.FIREBALL, Skill.LIGHTNING_STRIKE, Skill.ARCANE_SURGE, Skill.SILENCE
            ));

            return new Knight(
                    KnightName.MORGAN, KnightClass.MAGE,
                    120, 10, 45, 35, 60,
                    skills
            );
        } else if (name == KnightName.MERLIN) {
            List<Skill> skills = new ArrayList<>(Arrays.asList(
                    Skill.FIREBALL, Skill.LIGHTNING_STRIKE, Skill.ICE_BLAST, Skill.ARCANE_SURGE
            ));

            return new Knight(
                    KnightName.MERLIN, KnightClass.MAGE,
                    130, 5, 55, 40, 55,
                    skills
            );
        } else return null;
    }
}
