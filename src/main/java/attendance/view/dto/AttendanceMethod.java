package attendance.view.dto;

import attendance.exception.AttendanceArgumentException;
import java.util.Arrays;

public enum AttendanceMethod {
    
    ATTENDANCE("1"),
    MODIFY("2"),
    ATTENDANCE_HISTORY("3"),
    CREW_DISMISS_VIEW("4"),
    QUIT("Q"),
    ELSE("0");

    private final String method;
    private static final String INVALID_METHOD = "유효하지 않은 메서드입니다.";

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
