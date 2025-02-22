package view;

import java.util.Arrays;

public enum Command {

    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    CHECK_ATTENDANCE_BY_CREW("3"),
    CHECK_DISMISSAL_CREW("4"),
    QUIT("Q");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command from(final String input) {
        return Arrays.stream(Command.values())
                .filter(command -> command.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."));
    }
}
