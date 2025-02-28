package domain.attendance;

import java.time.LocalDateTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    TARDY("지각"),
    ABSENCE("결석"),
    NONE("기록없음"),
    ;
    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus calculateStatus(LocalDateTime dateTime) {
        if (AttendanceTime.isAttendance(dateTime)) {
            return ATTENDANCE;
        }
        if (AttendanceTime.isTardy(dateTime)) {
            return TARDY;
        }
        return ABSENCE;
    }
}
