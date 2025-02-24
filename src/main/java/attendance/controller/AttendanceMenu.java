package attendance.controller;

import attendance.exception.ExceptionMessage;
import java.util.Arrays;

public enum AttendanceMenu {
    ADD("1"),
    UPDATE("2"),
    SEARCH("3"),
    RISK("4"),
    QUIT("Q");

    private final String command;

    AttendanceMenu(String command) {
        this.command = command;
    }

    public static AttendanceMenu parse(String input) {
        return Arrays.stream(values())
                .filter(menu -> menu.command.equals(input.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.INVALID_MENU_COMMAND.getContent()));
    }
}
