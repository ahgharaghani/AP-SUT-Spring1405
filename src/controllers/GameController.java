package controllers;

import dto.InGameKnightDetailsDTO;
import models.*;
import models.abilities.Buff;
import models.enums.KnightClass;
import models.enums.Skill;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    public static String showTurn() {
        GameSession session = App.getCurrentGameSession();
        GameSession.Turn whoseTurn = session.getWhoseTurn();
        String currentPlayerName = session.getCurrentPlayer().getUsername();
        String currentKnightName = session.getCurrentPlayerKnight().getStringName();
        return "you are now playing as " + currentPlayerName + "'s " + currentKnightName;
    }

    public static String skipTurn() {
        GameSession session = App.getCurrentGameSession();
        session.incrementTurn();
        String currentKnightName = session.getCurrentPlayerKnight().getStringName();
        return currentKnightName + " is playing...";
    }

    public static InGameKnightDetailsDTO showDetails() {
        GameSession session =  App.getCurrentGameSession();
        Knight currentKnight = session.getCurrentPlayerKnight();
        List<String> skillNames = new ArrayList<>();
        List<Integer> skillAPs = new ArrayList<>();
        List<Integer> buffsApplied = new ArrayList<>();
        for (Skill skill : currentKnight.getSkills()) {
            skillNames.add(skill.getName());
            skillAPs.add(skill.getApReq());
        }
        for (Buff buff: currentKnight.getBuffsApplied()) {
            buffsApplied.add((int) (buff.getModifier() * 100));
        }

        return new InGameKnightDetailsDTO(
                session.getCurrentPlayer().getUsername(),
                currentKnight.getStringName(),
                skillNames,
                skillAPs,
                currentKnight.getAp(),
                buffsApplied
        );
    }

    public static String showStats(String knightNameStr, String username) {
        GameSession session = App.getCurrentGameSession();
        if (!session.isUserInGame(username)) return "player doesn't exist";
        List<Knight> knights = session.getKnightsOfUser(username);
        Knight knight = knights.stream()
                .filter(k -> k.getStringName().equalsIgnoreCase(knightNameStr))
                .findFirst()
                .orElse(null);
        if (knight == null) return "knight doesn't exist";

        return "name: " + knight.getStringName() +
                " - class: " + knight.getKnightClass() + "\n" +
                "HP: " + knight.getHp() +
                " - attack: " + knight.getAttack() +
                " - magic attack: " + knight.getMagicAttack() +
                " - defense: " + knight.getDefense() +
                " - speed: " + knight.getSpeed();
    }

    public static String attack(String knightNameStr) {
        GameSession session = App.getCurrentGameSession();
        Knight currentKnight = session.getCurrentPlayerKnight();
        Knight teammate = session.getTeammate();
        List<Knight> oppKnights = session.getEnemyKnights();
        Knight knightToBeAttacked = oppKnights.stream()
                .filter(k -> k.getStringName().equalsIgnoreCase(knightNameStr))
                .findFirst()
                .orElse(null);
        if (knightToBeAttacked == null && currentKnight.getStringName().equalsIgnoreCase(knightNameStr))
            return "you can't attack yourself";
        if (knightToBeAttacked == null && teammate.getStringName().equalsIgnoreCase(knightNameStr))
            return "you can't attack your own teammate";
        if (knightToBeAttacked == null)
            return "enemy doesn't exist";

        boolean willDodge = false;
        KnightClass currentClass = currentKnight.getKnightClass();

        if (currentClass == KnightClass.COMMANDER || currentClass == KnightClass.WARRIOR) {
            if (currentKnight.getAttack() < knightToBeAttacked.getSpeed())
                willDodge = true;
        } else if (currentClass == KnightClass.MAGE || currentClass == KnightClass.HEALER) {
            if (currentKnight.getMagicAttack() < knightToBeAttacked.getSpeed()) {
                willDodge = true;
            }
        }

        if (willDodge) {
            session.incrementTurn();
            String nextKnight = session.getCurrentPlayerKnight().getStringName();
            return "shokhosh enemy dodge dad\n" + nextKnight + " is playing...";
        }

        int attackPower = currentKnight.getAttack();
        int magicAttackPower = currentKnight.getMagicAttack();
        int defense = knightToBeAttacked.getDefense();

        int damage = Math.max(0,
                (attackPower + magicAttackPower) / 2 - (int)(0.3 * defense)
        );

        knightToBeAttacked.receiveDamage(damage);

        StringBuilder result = new StringBuilder();
        result.append("you dealt ").append(damage).append(" receiveDamageAndGetHP to ")
                .append(knightToBeAttacked.getStringName());

        if (knightToBeAttacked.getHp() <= 0) {
            result.append("\n").append(knightToBeAttacked.getStringName())
                    .append(" is dead!");

            if (session.anyWinner() != 0) {
                session.setAsConcluded();
                return result.toString();
            }
        }

        session.incrementTurn();
        String nextKnight = session.getCurrentPlayerKnight().getStringName();
        result.append("\n").append(nextKnight).append(" is playing...");

        return result.toString();
    }

    public static String skill(String skillName, String knightName) {
        GameSession session = App.getCurrentGameSession();
        Knight caster = session.getCurrentPlayerKnight();

        Skill skill = Skill.fromName(skillName);
        if (skill == null) return "skill doesn't exist";
        if (!caster.getSkills().contains(skill)) return "you don't have this skill";

        boolean targetProvided = knightName != null && !knightName.trim().isEmpty();

        if (targetProvided && !skill.needsTarget())
            return "this skill doesn't need target";

        List<Knight> candidates = skill.getTargetSide() == Skill.TargetSide.ALLY
                ? session.getAllyKnights()
                : session.getEnemyKnights();

        Knight target = null;
        if (skill.needsTarget()) {
            if (!targetProvided) return "this skill needs a target";
            target = findKnightInList(candidates, knightName, skill);
            if (target == null) return "selected knight doesn't exist";
        }

        if (caster.getAp() < skill.getApReq()) return "you don't have enough AP";

        caster.spendAp(skill.getApReq());

        String result;
        if (skill.needsTarget()) {
            result = skill.execute(caster, target);
        } else {
            result = skill.execute(caster, session.getAliveKnights(candidates));
        }

        result = result.trim();

        if (session.anyWinner() != 0) {
            session.setAsConcluded();
            return result;
        }

        session.incrementTurn();
        return result + "\n"
                + session.getCurrentPlayerKnight().getStringName() + " is playing...";
    }

    private static Knight findKnightInList(List<Knight> candidates, String knightName, Skill skill) {
        for (Knight k : candidates) {
            if (!k.getStringName().equalsIgnoreCase(knightName.trim())) continue;
            if (skill == Skill.REVIVE) return k;
            if (k.getHp() > 0) return k;
            return null;
        }
        return null;
    }
}
