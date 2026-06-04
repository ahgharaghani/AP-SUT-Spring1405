package models.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ScoreboardCommand implements Command {
    SHOW_SCOREBOARD("show scoreboard", "^\\s*show\\s+scoreboard\\s+-t\\s+(\\S+)\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("sort", matcher.group(1));
            return params;
        }
    },

    BACK("back", "^\\s*back\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    };

    private final String name;
    private final Pattern pattern;

    ScoreboardCommand(String name, String regex) {
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