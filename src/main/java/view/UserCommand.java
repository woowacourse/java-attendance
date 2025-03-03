package view;

import java.util.Arrays;

public enum UserCommand {
    CHECK_ATTENDANCE("1"),
    UPDATE_ATTENDANCE("2"),
    CHECK_ATTENDANCE_HISTORY("3"),
    CHECK_RISK_OF_EXPULSION_CREWS("4"),
    QUIT("Q");

    private final String command;

    UserCommand(final String command) {
        this.command = command;
    }

    public static UserCommand of(final String command) {
        return Arrays.stream(values())
                .filter(c -> c.command.equals(command))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
