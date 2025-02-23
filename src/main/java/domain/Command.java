package domain;

import java.util.Arrays;

public enum Command {
    UNKNOWN(null),
    ATTEND("1"),
    EDIT("2"),
    SEARCH_ATTEND("3"),
    SEARCH_WARNING_CREW("4");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command judgeCommand(String command) {
        return Arrays.stream(Command.values())
                .filter(com -> com.command != null)
                .filter(com -> com.command.equals(command))
                .findAny()
                .orElse(UNKNOWN);
    }
}
