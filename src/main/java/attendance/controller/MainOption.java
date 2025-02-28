package attendance.controller;

import static attendance.error.ErrorMessage.NO_MAIN_OPTION;

import java.util.Arrays;

public enum MainOption {
    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    VIEW_CREW_HISTORY("3"),
    CHECK_WARNING("4"),
    QUIT("Q");

    private final String command;

    MainOption(String command) {
        this.command = command;
    }

    public static MainOption from(String command) {
        return Arrays.stream(MainOption.values())
                .filter(option -> option.command.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NO_MAIN_OPTION));
    }

}
