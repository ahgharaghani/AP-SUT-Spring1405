package models.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainCommand implements Command {
    LOGOUT("logout", "^\\s*logout\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },

    SHOW_KNIGHT_DETAILS("show knight details", "^\\s*show\\s+knights\\s+details\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },

    SCOREBOARD("scoreboard", "^\\s*scoreboard\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },

    PLAY_AGAINST("play against", "^\\s*play\\s+against\\s+(\\S+)(?:\\s+.*)?$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("username", matcher.group(1));
            return params;
        }
    };

    private final String name;
    private final Pattern pattern;

    MainCommand(String name, String regex) {
        this.name = name;
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
