package models.commands;

import com.sun.beans.editors.StringEditor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommand implements Command{
    SHOW_TURN("show turn", "^\\s*show\\s+turn\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },

    SKIP_TURN("skip turn", "^\\s*skip\\s+turn\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },

    SHOW_DETAILS("show details", "^\\s*show\\s+details\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },

    SHOW_STATS("show stats", "^\\s*show\\s+stats\\s+-k\\s+(\\S+)\\s+-u\\s+(\\S+)(?:\\s+.*)?$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("knight", matcher.group(1));
            params.put("username", matcher.group(2));
            return params;
        }
    },

    ATTACK("attack", "^\\s*attack\\s+-k\\s+(\\S+)(?:\\s+.*)?$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("Knight", matcher.group(1));
            return params;
        }
    },

    SKILL("skill", "^\\s*skill\\s+-s\\s+([\\w\\s]+?)(?:\\s+-k\\s+(\\S+))?(?:\\s+.*)?$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("skill", matcher.group(1));
            params.put("knight", matcher.group(2));
            return params;
        }
    };

    private final String name;
    private final Pattern pattern;

    GameCommand(String name, String regex) {
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
