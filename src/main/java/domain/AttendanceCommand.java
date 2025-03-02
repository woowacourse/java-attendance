package domain;

import java.util.Arrays;
import java.util.Objects;

public enum AttendanceCommand {
    CHECK("1"),
    MODIFY("2"),
    LOOK_UP("3"),
    PENALTY_CHECK("4"),
    QUIT("Q");

    private final String command;

    AttendanceCommand(final String command) {
        this.command = command;
    }

    public static AttendanceCommand findByCommand(final String command) {
        return Arrays.stream(AttendanceCommand.values())
                .filter(attendanceCommand -> Objects.equals(attendanceCommand.command, command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 기능 입니다."));
    }
}
