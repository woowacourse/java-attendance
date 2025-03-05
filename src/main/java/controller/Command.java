package controller;

import java.util.Arrays;

public enum Command {
    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    ATTENDANCE_HISTORY_BY_CREW("3"),
    CHECK_DISMISSAL_CREW("4"),
    QUIT("Q");

    private final String input;

    Command(final String input) {
        this.input = input;
    }

    public static Command from(final String input) {
        return Arrays.stream(values())
                .filter(value -> value.input.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 입력입니다."));
    }
}
