package model.dto;

import model.player.Player;
import model.weapon.Weapon;

import java.util.LinkedList;
import java.util.List;

public class PlayerStatsDTO {
    private final String playerId;
    private final String playerType;
    private final int baseScore;
    private final String strategy;
    private final List<WeaponStatsDTO> weapons;

    public PlayerStatsDTO(Player player) {
        this.playerId = player.getPlayerId();
        this.playerType = player.getType();
        this.baseScore = player.getBaseScore();
        this.strategy = player.getStrategyName();
        this.weapons = new LinkedList<>();
        for (Weapon weapon : player.getWeapons()) {
            this.weapons.add(new WeaponStatsDTO(weapon));
        }
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerType() {
        return playerType;
    }

    public int getBaseScore() {
        return baseScore;
    }

    public String getStrategy() {
        return strategy;
    }

    public List<WeaponStatsDTO> getWeapons() {
        return weapons;
    }
}
