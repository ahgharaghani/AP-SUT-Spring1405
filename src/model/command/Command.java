package model.command;

public interface Command {
    boolean matches(String query);
    String getParameter(String param);
}
