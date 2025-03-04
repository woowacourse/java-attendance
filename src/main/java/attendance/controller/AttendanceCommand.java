package attendance.controller;

import java.util.Arrays;

public enum AttendanceCommand {
    CHECK_ATTENDANCE("1", "출석 확인"),
    MODIFY_ATTENDANCE("2", "출석 수정"),
    VIEW_ATTENDANCE_RECORD("3", "크루별 출석 기록 확인"),
    VIEW_WARNING_CREWS("4", "제적 위험자 확인"),
    EXIT("Q", "종료");

    private final String command;
    private final String description;

    AttendanceCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static AttendanceCommand from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기능 번호입니다."));
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
