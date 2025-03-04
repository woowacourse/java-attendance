package domain;

import java.util.Arrays;

public enum Command {
    ADD("1"),
    EDIT("2"),
    SEARCH("3"),
    FIND_WARNING("4"),
    EXIT("Q");

    private final String command;

    Command(final String command) {
        this.command = command;
    }

    public static Command findCommand(String targetCommand) {
        return Arrays.stream(Command.values())
                .filter(command -> command.command.equals(targetCommand))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어 입니다."));
    }
}
