package models.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GlobalCommand implements Command {
    CHANGE_MENU("change menu", "^\\s*change\\s+menu\\s*:\\s+(.+)\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("menu", matcher.group(1).trim());
            return params;
        }
    },
    SHOW_CURRENT_MENU("show current menu", "^\\s*show\\s+current\\s+menu\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },
    TIME_SET("time set", "^\\s*time\\s+set\\s+-d\\s+(.+)\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("date", matcher.group(1).trim());
            return params;
        }
    };

    private final String name;
    private final Pattern pattern;

    GlobalCommand(String name, String regex) {
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

