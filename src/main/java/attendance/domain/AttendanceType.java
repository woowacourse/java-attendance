package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceType {
    LATE;

    public static AttendanceType calculate(DayOfWeek dayOfWeek, LocalTime attendanceTime) {
        return null;
    }
}
