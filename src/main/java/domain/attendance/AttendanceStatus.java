package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    TARDY("지각"),
    ABSENCE("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus calcAttendanceStatus(DayOfWeek day, LocalTime attendanceTime) {
        if (TimeTable.isOverAbsenceTimeLimit(day, attendanceTime)) {
            return ABSENCE;
        }
        if (TimeTable.isOverTardyTimeLimit(day, attendanceTime)) {
            return TARDY;
        }
        return ATTENDANCE;
    }

    public String getStatus() {
        return status;
    }
}
