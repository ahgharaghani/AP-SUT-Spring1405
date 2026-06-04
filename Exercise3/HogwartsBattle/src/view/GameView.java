package view;

import model.command.GameCommand;
import controller.GameController;
import model.dto.PlayerStatsDTO;
import model.dto.WeaponStatsDTO;

import java.util.Scanner;

public class GameView {
    private static GameController gameController = GameController.getInstance();

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String query = scanner.nextLine();

            if (GameCommand.CREATE_PLAYER.matches(query)) {
                String playerId = GameCommand.CREATE_PLAYER.getParameter("playerId");
                String playerType = GameCommand.CREATE_PLAYER.getParameter("playerType");

                String result = gameController.createPlayer(playerId, playerType);
                if (result != null) System.out.println(result);
            } else if (GameCommand.FORGE_WEAPON.matches(query)) {
                String playerId = GameCommand.FORGE_WEAPON.getParameter("playerId");
                String weaponId = GameCommand.FORGE_WEAPON.getParameter("weaponId");
                String weaponType = GameCommand.FORGE_WEAPON.getParameter("weaponType");

                String result = gameController.forgeWeapon(playerId, weaponId, weaponType);
                if (result != null) System.out.println(result);
            } else if (GameCommand.ENCHANT.matches(query)) {
                String weaponId = GameCommand.ENCHANT.getParameter("weaponId");
                String enchantType = GameCommand.ENCHANT.getParameter("enchantType");

                String result = gameController.enchantWeapon(weaponId, enchantType);
                if (result != null) System.out.println(result);
            } else if (GameCommand.SET_STRATEGY.matches(query)) {
                String playerId = GameCommand.SET_STRATEGY.getParameter("playerId");
                String strategyType = GameCommand.SET_STRATEGY.getParameter("strategyType");

                String result = gameController.setStrategyForPlayer(playerId, strategyType);
                if (result != null) System.out.println(result);
            } else if (GameCommand.UPGRADE.matches(query)) {
                String weaponId = GameCommand.UPGRADE.getParameter("weaponId");

                String result = gameController.upgradeWeapon(weaponId);
                if (result != null) System.out.println(result);
            } else if (GameCommand.SHOW_PLAYER.matches(query)) {
                String playerId = GameCommand.SHOW_PLAYER.getParameter("playerId");

                PlayerStatsDTO result = gameController.getPlayerStats(playerId);
                if (result == null) System.out.println("Player not found");
                else System.out.println(parsePlayerDetails(result));
            } else if (GameCommand.SHOW_STATS.matches(query)) {
                String weaponId = GameCommand.SHOW_STATS.getParameter("weaponId");

                WeaponStatsDTO result = gameController.getWeaponStats(weaponId);
                if (result == null) System.out.println("Weapon not found");
                else System.out.println(parseWeaponDetails(result));
            } else if (GameCommand.SHOW_GOLD.matches(query)) {
                int result = gameController.getGoldCoins();
                System.out.println(result);
            } else if (GameCommand.ATTACK.matches(query)) {
                String playerId = GameCommand.ATTACK.getParameter("playerId");
                String weaponId = GameCommand.ATTACK.getParameter("weaponId");

                String result = gameController.attack(playerId, weaponId);
                System.out.println(result);
            }

            else if (GameCommand.FINISH.matches(query)) {
                break;
            } else {
                System.out.println("Invalid command");
            }
        }

        scanner.close();
    }

    private static String parsePlayerDetails(PlayerStatsDTO dto) {
        StringBuilder sb = new StringBuilder();
        sb.append("Player: ").append(dto.getPlayerType())
                .append(" - Base Score: ").append(dto.getBaseScore())
                .append(" - Strategy: ").append(dto.getStrategy());
        sb.append("\n");
        sb.append("Weapons:\n");
        for (WeaponStatsDTO weapon : dto.getWeapons()) {
            sb.append("  ").append(weapon.getWeaponId()).append(": ").append(weapon.getName())
                    .append(" - Level: ").append(weapon.getLevel())
                    .append(" - Base Damage: ").append(weapon.getBaseDamage())
                    .append(" - Total Damage: ").append(weapon.getTotalDamage());
            sb.append("\n");
        }

        return sb.toString();
    }

    private static String parseWeaponDetails(WeaponStatsDTO dto) {
        StringBuilder sb = new StringBuilder();
        sb.append(dto.getName())
                .append(" - Level: ").append(dto.getLevel())
                .append(" - Damage: ").append(dto.getTotalDamage())
                .append(" - owner: ").append(dto.getOwnerId());
        sb.append("\n");

        return sb.toString();
    }
}
