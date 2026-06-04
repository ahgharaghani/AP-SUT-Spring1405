package model.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StudioCommand implements Command {
    BUILD_RIG("^\\s*BUILD_RIG\\s+-c\\s+(?<channel>\\S+)(\\s+-p\\s+(?<pedals>.+))?$"),

    SET_CHANNEL("^\\s*SET_CHANNEL\\s+(?<channel>\\S+)\\s*"),

    PLAY("^\\s*PLAY\\s+\"(?<riff>.*)\"\\s*$"),

    EXIT("^\\s*EXIT\\s*$")
    ;

    private Pattern pattern;
    private Matcher matcher;

    StudioCommand(String regex) {
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