package attendance.model.attendance;

import java.time.LocalDateTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    ABSENCE("결석"),
    LATE("지각");

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static AttendanceStatus from(LocalDateTime dateTime) {
        return ATTENDANCE;
    }
}
