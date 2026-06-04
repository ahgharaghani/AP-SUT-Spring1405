package model.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommand implements Command {
    CREATE_PLAYER("^\\s*create_player\\s+(?<playerType>warrior|archer|mage)\\s+(?<playerId>\\S+)\\s*$"),
    FORGE_WEAPON("^\\s*forge\\s+(?<playerId>\\S+)\\s+(?<weaponId>\\S+)\\s+(?<weaponType>sword|axe|bow|dagger|staff|wand)\\s*$"),

    ENCHANT("^\\s*enchant\\s+(?<weaponId>\\S+)\\s+(?<enchantType>fire|poison|lightning)\\s*$"),

    SET_STRATEGY("^\\s*set\\s+strategy\\s+(?<playerId>\\S+)\\s+(?<strategyType>aggressive|defensive|balanced)\\s*$"),

    UPGRADE("^\\s*upgrade\\s+(?<weaponId>\\S+)\\s*$"),

    SHOW_PLAYER("^\\s*show\\s+player\\s+(?<playerId>\\S+)\\s*$"),
    SHOW_STATS("^\\s*show\\s+stats\\s+(?<weaponId>\\S+)\\s*$"),
    SHOW_GOLD("^\\s*show\\s+gold\\s*$"),

    ATTACK("^\\s*attack\\s+(?<playerId>\\S+)\\s+(?<weaponId>\\S+)\\s*$"),

    FINISH("^\\s*finish\\s*$")
    ;

    private Pattern pattern;
    private Matcher matcher;

    GameCommand(String regex) {
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean matches(String query) {
        return (matcher = pattern.matcher(query)).matches();
    }

    @Override
    public String getParameter(String param) {
        return matcher.group(param);
    }
}