package models.commands;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {

    class ParsedCommand {
        private final Command command;
        private final Map<String, String> params;

        public ParsedCommand(Command command, Map<String, String> params) {
            this.command = command;
            this.params = params;
        }

        public Command getCommand() {
            return command;
        }

        public String getParam(String key) {
            return params.get(key);
        }
    }

    String getName();
    Pattern getPattern();
    Map<String, String> extractParams(Matcher matcher);

    default boolean matches(String input) {
        if (input == null) {
            return false;
        }
        String trimmed = input.trim().replaceAll("\\s+", " ");
        return getPattern().matcher(trimmed).matches();
    }

    default ParsedCommand parse(String input) {
        if (input == null) {
            return null;
        }
        String trimmed = input.trim().replaceAll("\\s+", " ");
        Matcher matcher = getPattern().matcher(trimmed);
        if (matcher.matches()) {
            return new ParsedCommand(this, extractParams(matcher));
        }
        return null;
    }
}
