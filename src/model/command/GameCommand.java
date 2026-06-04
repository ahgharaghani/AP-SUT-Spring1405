package model.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommand implements Command{
    SHOW_ANIMAL_DETAILS("^\\s*show\\s+animals\\s+details\\s*$"),
    SHOW_RULES("^\\s*show\\s+rules\\s*$"),
    SHOW_GOVERNORS_HISTORY("^\\s*show\\s+governors\\s+history\\s*$"),

    VOTE("^\\s*vote\\s+-t\\s+(?<tong>\\S+)\\s*$"),
    VOTE_GOVERNOR("^\\s*vote\\s+governor\\s+-id\\s+(?<id>\\S+)\\s*$"),

    WORK("^\\s*work\\s+-h\\s+(?<hours>\\S+)\\s*$"),

    SPREAD_RUMOR("^\\s*spread\\s+rumor\\s+-t\\s+(?<id>\\S+)\\s+-r\\s+(?<type>\\S+)\\s*$"),

    CHOOSE_SHERIFF("^\\s*choose\\s+a\\s+new\\s+sheriff\\s+-id\\s+(?<id>\\d+)\\s*$"),

    SET_TRADE_RATE("^\\s*set\\s+trade\\s+rate\\s+-p\\s+(?<percent>\\S+)\\s*$"),

    CHANGE_RULE("^\\s*change\\s+rule\\s+-from\\s+\"(?<oldRule>[^\"]+)\"\\s+-to\\s+\"(?<newRule>[^\"]+)\"\\s*$"),

    REBEL("^\\s*rebel\\s+-n\\s+(?<candidate>\\S+)\\s+-t\\s+(?<tong>\\S+)\\s*$"),

    BACK_TO_MAIN_MENU("^\\s*Back\\s+to\\s+main\\s+menu\\s*$"),
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
