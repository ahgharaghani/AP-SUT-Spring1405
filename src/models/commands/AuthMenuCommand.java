package models.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AuthMenuCommand implements Command {
    CREATE_GUEST("create guest", "^create guest\\s+-u\\s+(\\S+)\\s+-p\\s+(\\S+)\\s+-e\\s+(\\S+)$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("username", matcher.group(1));
            params.put("password", matcher.group(2));
            params.put("email", matcher.group(3));
            return params;
        }
    },
    CREATE_HOST("create host", "^create host\\s+-u\\s+(\\S+)\\s+-b\\s+\"([^\"]+)\"\\s+-p\\s+(\\S+)\\s+-e\\s+(\\S+)$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("username", matcher.group(1));
            params.put("brand", matcher.group(2));
            params.put("password", matcher.group(3));
            params.put("email", matcher.group(4));
            return params;
        }
    },
    LOGIN_GUEST("login guest", "^login as guest\\s+-u\\s+(\\S+)\\s+-p\\s+(\\S+)$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("username", matcher.group(1));
            params.put("password", matcher.group(2));
            return params;
        }
    },
    LOGIN_HOST("login host", "^login as host\\s+-u\\s+(\\S+)\\s+-p\\s+(\\S+)$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("username", matcher.group(1));
            params.put("password", matcher.group(2));
            return params;
        }
    },
    LOGOUT("logout", "^logout$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },
    FORGOT_PASSWORD_GUEST("forgot password guest", "^forgot password guest\\s+-u\\s+(\\S+)\\s+-e\\s+(\\S+)$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("username", matcher.group(1));
            params.put("email", matcher.group(2));
            return params;
        }
    },
    FORGOT_PASSWORD_HOST("forgot password host", "^forgot password host\\s+-u\\s+(\\S+)\\s+-e\\s+(\\S+)$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("username", matcher.group(1));
            params.put("email", matcher.group(2));
            return params;
        }
    };

    private final String name;
    private final Pattern pattern;

    AuthMenuCommand(String name, String regex) {
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