package models.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ExploreMenuCommand implements Command {
    SEARCH_STAYS("search stays", "^search stays\\s+-city\\s+\"([^\"]+)\"\\s+-guests\\s+(\\S+)$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("city", matcher.group(1));
            params.put("guests", matcher.group(2));
            return params;
        }
    },
    SHOW_STAY("show stay", "^show stay\\s+-name\\s+(.+)$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("stay name", matcher.group(1).trim());
            return params;
        }
    };

    private final String name;
    private final Pattern pattern;

    ExploreMenuCommand(String name, String regex) {
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
