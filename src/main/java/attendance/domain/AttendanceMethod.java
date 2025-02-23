package attendance.domain;

import java.util.Arrays;

import attendance.common.exception.AttendanceArgumentException;

public enum AttendanceMethod {
    ATTENDANCE("1"),
    MODIFY("2"),
    ATTENDANCE_HISTORY("3"),
    CREW_DISMISS_VIEW("4"),
    QUIT("Q");

    private static final String INVALID_METHOD = "유효하지 않은 메서드입니다.";

    private final String method;

    public static AttendanceMethod of(String requestMethod) {
        return Arrays.stream(AttendanceMethod.values())
            .filter((attendanceMethod) -> attendanceMethod
                .method
                .equals(requestMethod))
            .findFirst()
            .orElseThrow(() -> new AttendanceArgumentException(INVALID_METHOD));
    }

    AttendanceMethod(String method) {
        this.method = method;
    }
}
