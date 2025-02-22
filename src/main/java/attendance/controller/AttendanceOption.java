package attendance.controller;

import attendance.common.ErrorMessage;
import java.util.Arrays;

public enum AttendanceOption {

    MARK("1"),
    EDIT("2"),
    CHECK("3"),
    WARNING("4"),
    QUIT("Q");

    private final String input;

    AttendanceOption(String input) {
        this.input = input;
    }

    public static AttendanceOption find(String input) {
        return Arrays.stream(values())
                .filter(attendanceOption -> input.equals(attendanceOption.input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_OPTION_INPUT.getMessage()));
    }
}
