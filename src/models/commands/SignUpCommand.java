package models.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpCommand implements Command {
    SIGNUP("signup", "^\\s*signup\\s+-username\\s+(\\S+)\\s+-password\\s+(\\S+)(?:\\s+.*)?$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("username", matcher.group(1));
            params.put("password", matcher.group(2));
            return params;
        }
    },
    LOGIN("login", "^\\s*login\\s+-username\\s+(\\S+)\\s+-password\\s+(\\S+)(?:\\s+.*)?$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("username", matcher.group(1));
            params.put("password", matcher.group(2));
            return params;
        }
    };

    private final String name;
    private final Pattern pattern;

    SignUpCommand(String name, String regex) {
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
