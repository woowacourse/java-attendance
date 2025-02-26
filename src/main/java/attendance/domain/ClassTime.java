package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public enum ClassTime {
    ;

    public static int calculateAttendanceDifference(final LocalDateTime attendanceDateTime) {
        LocalTime monday = LocalTime.of(13, 0);
        return (attendanceDateTime.toLocalTime().toSecondOfDay() - monday.toSecondOfDay()) / 60;
    }
}
