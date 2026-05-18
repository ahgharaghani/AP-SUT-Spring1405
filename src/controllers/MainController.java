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
        App.setCurrentGameSession(null);
        App.setCurrentMenu(Menu.SIGNUP);
        return "logged out successfully";
    }

    public static List<KnightDTO> showKnightDetails() {
        List<KnightDTO> knightDTOs = new ArrayList<>();

        for (KnightName knightName : KnightName.values()) {
            Knight knight = KnightFactory.getKnight(knightName);
            List<String> skills = new ArrayList<>();
            for (Skill skill : knight.getSkills()) {
                skills.add(skill.getName());
            }

            knightDTOs.add(
                    new KnightDTO(
                            knightName.getName(),
                            knight.getKnightClass().toString(),
                            knight.getHp(),
                            knight.getAttack(),
                            knight.getMagicAttack(),
                            knight.getDefense(),
                            knight.getSpeed(),
                            skills
                    )
            );
        }

        return knightDTOs;
    }

    public static String gotoScoreBoard() {
        App.setCurrentMenu(Menu.SCOREBOARD);
        return "you're now in scoreboard menu";
    }

    public static String playAgainst(String username) {
        username = username.trim();
        User currentUser = App.getCurrentPlayer();
        if (currentUser.getUsername().equals(username)) {
            return "you can't play with yourself";
        }

        User opponent = Repository.getPlayerByUsername(username);
        if (opponent == null) {
            return "invalid player name";
        }

        App.setCurrentGameSession(new GameSession(currentUser, opponent));
        return "you're playing with " + username;
    }

    public static String whoMustChoose() {
        return App.getCurrentGameSession().whoMustChooseKnight();
    }

    public static String chooseKnight(String knightName, String playerName) {
        GameSession session = App.getCurrentGameSession();
        int whichPlayer = session.usernameIsWhichPlayer(playerName);
        KnightName name = KnightName.fromString(knightName);

        if (name == null) return "invalid knight name";

        if ((whichPlayer == 1 && session.isFirstPlayerKnightAlreadySelected(name)) ||
                (whichPlayer == 2 && session.isSecondPlayerKnightAlreadySelected(name))) {
            return "you've already chosen this knight";
        }

        if (whichPlayer == 1) session.addKnightToFirstPlayer(name);
        else session.addKnightToSecondPlayer(name);

        if (session.shallWeBegin()) {
            session.setAsOngoing();
            App.setCurrentMenu(Menu.GAME);
        }

        return "knight selected successfully";
    }
}