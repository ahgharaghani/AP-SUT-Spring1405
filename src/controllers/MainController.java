package controllers;

import dto.KnightDTO;
import factories.KnightFactory;
import models.*;
import models.enums.KnightName;
import models.enums.Menu;
import models.enums.Skill;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    public static String logout() {
        App.setCurrentPlayer(null);
        App.setCurrentMenu(Menu.MAIN);
        return "logged out successfully";
    }

    public static List<KnightDTO> showKnightDetails() {
        List<KnightDTO> knightDTOs = new ArrayList<>();

        for (KnightName knightName : KnightName.values()) {
            Knight knight = KnightFactory.getKnight(knightName);
            String name = knightName.getName();
            String classType = knight.getKnightClass().toString();
            int hp = knight.getHp();
            int attack = knight.getAttack();
            int magic = knight.getMagicAttack();
            int defense = knight.getDefense();
            int speed = knight.getSpeed();
            List<String> skills = new ArrayList<>();
            for (Skill skill : knight.getSkills()) {
                skills.add(skill.toString());
            }

            knightDTOs.add(
                    new KnightDTO(name, classType, hp, attack, magic, defense, speed, skills)
            );
        }

        return knightDTOs;
    }

    public static String gotoScoreBoard() {
        App.setCurrentMenu(Menu.SCOREBOARD);
        return "you're now in scoreboard menu";
    }

    public static String playAgainst(String username) {
        User currentUser = App.getCurrentPlayer();
        if (currentUser.getUsername().equals(username)) {
            return "you can't play with yourself";
        }
        User opp = Repository.getPlayerByUsername(username);
        if (opp == null) {
            return "invalid player name";
        }

        GameSession session = new GameSession(currentUser, opp);
        App.setCurrentGameSession(session);
        return null;
    }

    public static String whoMustChoose() {
        return App.getCurrentGameSession().whoMustChooseKnight();
    }

    public static String chooseKnight(String knightName, String playerName) {
        GameSession session = App.getCurrentGameSession();
        int whichPlayer = session.usernameIsWhichPlayer(playerName);
        KnightName name = KnightName.fromString(knightName);
        if (name == null) return "invalid knight name";
        if ( (whichPlayer == 1 && session.isFirstPlayerKnightAlreadySelected(name)) ||
                (whichPlayer == 2 && session.isSecondPlayerKnightAlreadySelected(name))
        )
            return "you've already chosen this knight";

        if (whichPlayer == 1) session.addKnightToFirstPlayer(name);
        else session.addKnightToSecondPlayer(name);

        if (session.shallWeBegin()) {
            session.setAsOngoing();
            App.setCurrentMenu(Menu.GAME);
        }

        return "knight selected successfully";
    }

}
