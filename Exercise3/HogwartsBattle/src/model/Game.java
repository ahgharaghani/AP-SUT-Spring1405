package model;

import model.player.Player;
import model.weapon.Weapon;

import java.util.LinkedList;
import java.util.List;

public class Game {
    private static Game instance = null;

    private int gold;
    private List<Player> players;
    private List<Weapon> weapons; // keeps a reference to all the weapons

    private Game() {
        this.gold = 600;
        this.players = new LinkedList<>();
        this.weapons = new LinkedList<>();
    }

    public static Game getInstance() {
        if (instance == null) instance = new Game();
        return instance;
    }

    public int getGold() {
        return gold;
    }

    public Player findPlayerById(String playerId) {
        for (Player player : players) {
            if (player.getPlayerId().equals(playerId)) return player;
        }
        return null;
    }

    public boolean existsPlayerById(String playerId) {
        return findPlayerById(playerId) != null;
    }

    public Weapon findWeaponById(String weaponId) {
        for (Weapon weapon : weapons) {
            if (weapon.getWeaponId().equals(weaponId)) return weapon;
        }
        return null;
    }

    public boolean existsWeaponById(String weaponId) {
        return findWeaponById(weaponId) != null;
    }

    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }

    public void addWeapon(Weapon newWeapon) {
        weapons.add(newWeapon);
    }

    public void replaceWeapon(Weapon oldWeapon, Weapon newWeapon) {
        for (int i = 0; i < weapons.size(); i++) {
            if (weapons.get(i) == oldWeapon) {
                weapons.set(i, newWeapon);
                return;
            }
        }
    }

    public void costGold(int cost) {
        gold -= cost;
        if (gold < 0) gold = 0;
    }
}
