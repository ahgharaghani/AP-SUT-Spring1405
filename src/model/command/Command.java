package model.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    boolean matches(String query);
    String getParameter(String param);
}
