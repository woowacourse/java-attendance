package attendance.controller;

import java.util.Arrays;

public enum AttendanceOption {

    MARK("1"),
    EDIT("2"),
    CHECK("3"),
    WARNING("4"),
    QUIT("Q");

    private final String message;

    AttendanceOption(String message) {
        this.message = message;
    }

    public static AttendanceOption find(String input) {
        return Arrays.stream(values())
            .filter(attendanceOption -> attendanceOption.message.equals(input))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 기능을 입력하셨습니다."));
    }
}
