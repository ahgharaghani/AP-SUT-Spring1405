package models.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GlobalCommand implements Command{
    SHOW_CURRENT_MENU("show current menu", "^\\s*show\\s+current\\s+menu\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },
    EXIT("exit", "^\\s*exit\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
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
