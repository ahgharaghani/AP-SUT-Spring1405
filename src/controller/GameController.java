package controller;

import model.Game;
import model.dto.PlayerStatsDTO;
import model.dto.WeaponStatsDTO;
import model.player.Player;
import model.player.factory.PlayerFactory;
import model.strategy.Aggressive;
import model.strategy.AttackStrategy;
import model.strategy.Balanced;
import model.strategy.Defensive;
import model.weapon.Weapon;
import model.weapon.decorator.factory.EnchantFactory;

public class GameController {
    private static GameController instance = null;

    private Game game;

    private GameController() {
        this.game = Game.getInstance();
    }

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }

    public String createPlayer(String playerId, String playerType) {
        if (game.existsPlayerById(playerId)) return "Player id already exists";

        PlayerFactory factory = PlayerFactory.getFactory(playerType);

        int cost = factory.getCost();
        if (cost > game.getGold()) return "Not enough gold";

        Player newPlayer = factory.createPlayer(playerId);
        game.addPlayer(newPlayer);
        game.costGold(cost);
        return null;
    }

    public String forgeWeapon(String playerId, String weaponId, String weaponType) {
        Player player = game.findPlayerById(playerId);
        if (player == null) return "Player not found";

        if (game.existsWeaponById(weaponId)) return "Weapon id already exists";

        Weapon weapon = player.forgeWeapon(weaponType, weaponId);
        if (weapon == null) return "Weapon not allowed for this player";

        int cost = player.getCostOfWeapon(weaponType);
        if (cost > game.getGold()) return "Not enough gold";

        player.addWeapon(weapon);
        game.addWeapon(weapon);
        game.costGold(cost);
        return null;
    }

    public String enchantWeapon(String weaponId, String enchantType) {
        Weapon weapon = game.findWeaponById(weaponId);
        if (weapon == null) return "Weapon not found";

        EnchantFactory factory = EnchantFactory.getFactory(enchantType);
        int cost = factory.getCost();
        if (cost > game.getGold()) return "Not enough gold";

        Player owner = game.findPlayerById(weapon.getOwnerId());

        Weapon enchanted = factory.apply(weapon);
        owner.replaceWeapon(weapon, enchanted);
        game.replaceWeapon(weapon, enchanted);
        game.costGold(cost);

        return null;
    }

    public String setStrategyForPlayer(String playerId, String strategyType) {
        Player player = game.findPlayerById(playerId);
        if (player == null) return "Player not found";

        AttackStrategy newStrategy;
        switch (strategyType.toLowerCase()) {
            case "aggressive":
                newStrategy = new Aggressive();
                break;
            case "defensive":
                newStrategy = new Defensive();
                break;
            case "balanced":
                newStrategy = new Balanced();
                break;
            default:
                newStrategy = null;
        }
        player.setAttackStrategy(newStrategy);

        return null;
    }

    public String upgradeWeapon(String weaponId) {
        Weapon weapon = game.findWeaponById(weaponId);
        if (weapon == null) return "Weapon not found";

        if (weapon.getLevel() >= 5) return "Max level reached";

        int cost = 25;
        if (cost > game.getGold()) return "Not enough gold";

        weapon.upgradeLevel();
        game.costGold(cost);

        return null;
    }

    public PlayerStatsDTO getPlayerStats(String playerId) {
        Player player = game.findPlayerById(playerId);
        if (player == null) return null;

        return new PlayerStatsDTO(player);
    }

    public WeaponStatsDTO getWeaponStats(String weaponId) {
        Weapon weapon = game.findWeaponById(weaponId);
        if (weapon == null) return null;

        return new WeaponStatsDTO(weapon);
    }

    public int getGoldCoins() {
        return game.getGold();
    }

    public String attack(String playerId, String weaponId) {
        Player player = game.findPlayerById(playerId);
        if (player == null) return "Player not found";

        Weapon weapon = game.findWeaponById(weaponId);
        if (weapon == null) return "Weapon not found";

        if (!weapon.getOwnerId().equals(playerId)) return "Weapon does not belong to this player";

        double finalScore = player.attack(weapon);

        return player.getType() + " attacks with " + weapon.getName() + " (Level " + weapon.getLevel() + ")"
                + " using " + player.getStrategyName() + " strategy for " + (int) finalScore + " points!";
    }
}
