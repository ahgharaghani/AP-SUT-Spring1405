package models.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GuestMenuCommand implements Command {
    SHOW_BALANCE("show balance", "^\\s*show\\s+balance\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },
    CHARGE_ACCOUNT("charge account", "^\\s*charge\\s+account\\s*:\\s+\\$\\s*(\\d+(?:\\.\\d{1,2})?)\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("amount", matcher.group(1).trim());
            return params;
        }
    },
    REQUEST_BOOKING("request booking", "^\\s*request\\s+booking\\s+-name\\s+\"([^\"]+)\"\\s+-from\\s+(\\S+)\\s+-to\\s+(\\S+)\\s+-guests\\s+(\\S+)\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("stay name", matcher.group(1));
            params.put("from", matcher.group(2));
            params.put("to", matcher.group(3));
            params.put("guests", matcher.group(4));
            return params;
        }
    },
    LIST_MY_BOOKINGS("list my bookings", "^\\s*list\\s+my\\s+bookings\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            return new HashMap<>();
        }
    },
    CANCEL_BOOKING("cancel booking", "^\\s*cancel\\s+booking\\s+-id\\s+(\\S+)\\s*$") {
        @Override
        public Map<String, String> extractParams(Matcher matcher) {
            Map<String, String> params = new HashMap<>();
            params.put("booking id", matcher.group(1));
            return params;
        }
    };

    private final String name;
    private final Pattern pattern;

    GuestMenuCommand(String name, String regex) {
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