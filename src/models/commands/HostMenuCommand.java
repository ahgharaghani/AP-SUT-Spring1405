package models.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HostMenuCommand implements Command {
    SHOW_BALANCE("show balance", "^\\s*show\\s+balance\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },
    ADD_STAY("add stay", "^\\s*add\\s+stay\\s+-name\\s+\"([^\"]+)\"\\s+-city\\s+\"([^\"]+)\"\\s+-addr\\s+\"([^\"]+)\"\\s+-cap\\s+(\\S+)\\s+-ppn\\s+(\\S+)\\s+-cp\\s+(\\S+)\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("stay name", matcher.group(1));
            params.put("city", matcher.group(2));
            params.put("address", matcher.group(3));
            params.put("capacity", matcher.group(4));
            params.put("price per night", matcher.group(5));
            params.put("policy", matcher.group(6));
            return params;
        }
    },
    LIST_MY_STAYS("list my stays", "^\\s*list\\s+my\\s+stays\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },
    DEACTIVATE_STAY("deactivate stay", "^\\s*deactivate\\s+stay\\s+-name\\s+\"([^\"]+)\"\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("stay name", matcher.group(1));
            return params;
        }
    },
    ACTIVATE_STAY("activate stay", "^\\s*activate\\s+stay\\s+-name\\s+\"([^\"]+)\"\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("stay name", matcher.group(1));
            return params;
        }
    },
    LIST_BOOKING_REQUESTS("list booking requests", "^\\s*list\\s+booking\\s+requests\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },
    CONFIRM_BOOKING("confirm booking", "^\\s*confirm\\s+booking\\s+-id\\s+(\\S+)\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("booking id", matcher.group(1));
            return params;
        }
    },
    REJECT_BOOKING("reject booking", "^\\s*reject\\s+booking\\s+-id\\s+(\\S+)\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("booking id", matcher.group(1));
            return params;
        }
    };

    private final String name;
    private final Pattern pattern;

    HostMenuCommand(String name, String regex) {
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