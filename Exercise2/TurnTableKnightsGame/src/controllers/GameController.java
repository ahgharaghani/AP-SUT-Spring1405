package controllers;

import dto.GameResultDTO;
import models.*;
import models.abilities.Buff;
import models.enums.KnightClass;
import models.enums.Menu;
import models.enums.Skill;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GameController {
    public static String showTurn() {
        GameSession session = App.getCurrentGameSession();
        return "you are now playing as " +
                session.getCurrentPlayer().getUsername() +
                "'s " +
                session.getCurrentPlayerKnight().getStringName();
    }

    public static String skipTurn() {
        GameSession session = App.getCurrentGameSession();
        session.incrementTurn();
        return session.getCurrentPlayerKnight().getStringName() + " is playing...";
    }

    public static String showDetails() {
        GameSession session = App.getCurrentGameSession();
        Knight currentKnight = session.getCurrentPlayerKnight();

        StringBuilder sb = new StringBuilder();
        sb.append(session.getCurrentPlayer().getUsername())
                .append("'s ")
                .append(currentKnight.getStringName())
                .append(" details: \n");
        sb.append("--------------------\n");
        sb.append("skills: \n");

        for (Skill skill : currentKnight.getSkills()) {
            sb.append(skill.getName())
                    .append("-> AP: ")
                    .append(skill.getApReq())
                    .append("\n");
        }

        sb.append("--------------------\n");
        sb.append("AP: ").append(currentKnight.getAp()).append("\n");
        sb.append("--------------------\n");
        sb.append("charms: \n");

        boolean hasCharm = false;
        hasCharm |= appendCharmLine(sb, currentKnight, Buff.BuffParameter.ATTACK, "attack");
        hasCharm |= appendCharmLine(sb, currentKnight, Buff.BuffParameter.MAGIC_ATTACK, "magic attack");
        hasCharm |= appendCharmLine(sb, currentKnight, Buff.BuffParameter.DEFENSE, "defense");
        hasCharm |= appendCharmLine(sb, currentKnight, Buff.BuffParameter.SPEED, "speed");

        if (!hasCharm) {
            sb.append("you have no charms on yourself");
        } else {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    private static boolean appendCharmLine(StringBuilder sb, Knight knight,
                                           Buff.BuffParameter parameter, String parameterName) {
        Double modifier = knight.getBuffModifier(parameter);
        if (modifier == null) return false;

        sb.append(parameterName)
                .append(" got ")
                .append(modifier > 0 ? "buffed" : "nerfed")
                .append(" by: ")
                .append((int) Math.abs(modifier * 100))
                .append("%\n");
        return true;
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

    public static GameResultDTO attack(String knightNameStr) {
        GameSession session = App.getCurrentGameSession();
        Knight currentKnight = session.getCurrentPlayerKnight();
        Knight teammate = session.getTeammate();

        boolean enemyHasKnight = session.getEnemyKnights().stream()
                .anyMatch(k -> k.getStringName().equalsIgnoreCase(knightNameStr));

        if (!enemyHasKnight && currentKnight.getStringName().equalsIgnoreCase(knightNameStr))
            return new GameResultDTO("you can't attack yourself");
        if (!enemyHasKnight && teammate.getStringName().equalsIgnoreCase(knightNameStr))
            return new GameResultDTO("you can't attack your own teammate");

        Knight target = session.getEnemyKnights().stream()
                .filter(k -> k.getHp() > 0 && k.getStringName().equalsIgnoreCase(knightNameStr))
                .findFirst()
                .orElse(null);

        if (target == null)
            return new GameResultDTO("enemy doesn't exist");

        boolean willDodge = false;
        KnightClass currentClass = currentKnight.getKnightClass();

        if (currentClass == KnightClass.COMMANDER || currentClass == KnightClass.WARRIOR) {
            if (currentKnight.getAttack() < target.getSpeed()) willDodge = true;
        } else {
            if (currentKnight.getMagicAttack() < target.getSpeed()) willDodge = true;
        }

        if (willDodge) {
            session.incrementTurn();
            return new GameResultDTO("shokhosh enemy dodge dad\n" +
                    session.getCurrentPlayerKnight().getStringName() + " is playing...");
        }

        int damage = Math.max(
                0,
                (currentKnight.getAttack() + currentKnight.getMagicAttack()) / 2
                        - (int) Math.floor(0.3 * target.getDefense())
        );

        target.receiveDamage(damage);
        currentKnight.addDamageDealt(damage);

        StringBuilder result = new StringBuilder();
        result.append("you dealt ").append(damage).append(" damage to ").append(target.getStringName());

        if (target.getHp() == 0) {
            result.append("\n").append(target.getStringName()).append(" is dead!");
        }

        if (session.anyWinner() != 0) {
            result.append("\n").append(finishGame());
            return result.toString();
        }

        session.incrementTurn();
        result.append("\n").append(session.getCurrentPlayerKnight().getStringName()).append(" is playing...");
        return result.toString();
    }

    public static GameResultDTO skill(String skillName, String knightName) {
        GameSession session = App.getCurrentGameSession();
        Knight caster = session.getCurrentPlayerKnight();

        Skill skill = Skill.fromName(skillName);
        if (skill == null) return "skill doesn't exist";
        if (!caster.getSkills().contains(skill)) return "you don't have this skill";

        boolean targetProvided = knightName != null;

        if (targetProvided && !skill.needsTarget()) {
            return "this skill doesn't need target";
        }

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
            return result + "\n" + finishGame();
        }

        session.incrementTurn();
        return result + "\n" + session.getCurrentPlayerKnight().getStringName() + " is playing...";
    }

    private static Knight findKnightInList(List<Knight> candidates, String knightName, Skill skill) {
        for (Knight knight : candidates) {
            if (!knight.getStringName().equalsIgnoreCase(knightName.trim())) continue;
            if (skill == Skill.REVIVE) return knight;
            if (knight.getHp() > 0) return knight;
            return null;
        }
        return null;
    }

    private static GameResultDTO finishGame() {
        GameSession session = App.getCurrentGameSession();
        int winnerCode = session.anyWinner();

        User winner = (winnerCode == 1) ? session.getFirstPlayer() : session.getSecondPlayer();
        User loser = (winnerCode == 1) ? session.getSecondPlayer() : session.getFirstPlayer();

        List<Knight> winnerKnights = (winnerCode == 1) ? session.getFirstPlayerKnights() : session.getSecondPlayerKnights();
        List<Knight> loserKnights = (winnerCode == 1) ? session.getSecondPlayerKnights() : session.getFirstPlayerKnights();

        int winnerPoints = calculateTeamPoints(winnerKnights);
        int loserPoints = calculateTeamPoints(loserKnights);

        winner.addGamePlayed();
        loser.addGamePlayed();
        winner.addGameWon();
        winner.addPoints(winnerPoints);
        loser.addPoints(loserPoints);

        session.setAsConcluded();
        App.setCurrentMenu(Menu.MAIN);
        App.setCurrentGameSession(null);

        List<GameResultDTO.KnightResultDTO> winnerKnightDTOs = winnerKnights.stream()
                .map(k -> buildKnightResultDTO(winner, k))
                .collect(Collectors.toList());

        List<GameResultDTO.KnightResultDTO> loserKnightDTOs = loserKnights.stream()
                .map(k -> buildKnightResultDTO(loser, k))
                .collect(Collectors.toList());

        return new GameResultDTO(
                winner.getUsername(),
                loser.getUsername(),
                winnerPoints,
                loserPoints,
                winnerKnightDTOs,
                loserKnightDTOs
        );
    }

    private static GameResultDTO.KnightResultDTO buildKnightResultDTO(User user, Knight knight) {
        return new GameResultDTO.KnightResultDTO(
                user.getUsername(),
                knight.getStringName(),
                knight.getDamageDealt(),
                knight.getHp(),
                knight.getHp() > 0,
                calculateKnightPoint(knight)
        );
    }

    private static int calculateTeamPoints(List<Knight> knights) {
        int sum = 0;
        for (Knight knight : knights) {
            sum += calculateKnightPoint(knight);
        }
        return sum;
    }

    private static int calculateKnightPoint(Knight knight) {
        int damageTaken = Math.max(0, knight.getMaxHp() - knight.getHp());
        double c = (knight.getHp() > 0) ? 1.5 : 1;
        return (int) ((knight.getDamageDealt() - damageTaken) * c);
    }

}