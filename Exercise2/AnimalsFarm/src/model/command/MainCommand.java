package model.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainCommand implements Command {
    ADD("^\\s*add\\s+-n\\s+(?<name>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-r\\s+(?<role>\\S+)\\s*$"),
    START_GAME("^\\s*start\\s+game\\s*$"),
    EXIT("^\\s*exit\\s*$");

    private Pattern pattern;
    private Matcher matcher;

    MainCommand(String regex) {
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
