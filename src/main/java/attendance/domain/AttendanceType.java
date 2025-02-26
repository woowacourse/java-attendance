package attendance.domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public enum AttendanceType {
    LATE;

    public static AttendanceType calculate(DayOfWeek dayOfWeek, LocalTime attendanceTime) {
        LocalTime attendanceStartTime = getAttendanceStartTime(dayOfWeek);
        Duration duration = Duration.between(attendanceStartTime, attendanceTime);
        long minutes = duration.getSeconds() / 60;
        if (minutes > 5) {
            return LATE;
        }
        return null;
    }

    private static LocalTime getAttendanceStartTime(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return LocalTime.of(13, 0);
        }
        return LocalTime.of(10, 0);
    }
}
