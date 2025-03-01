package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceStatus {

    ATTENDANCE(0),
    LATE(5),
    ABSENCE(30);

    private final int limit;

    AttendanceStatus(final int limit) {
        this.limit = limit;
    }

    public static AttendanceStatus findByTime(final DayOfWeek dayOfWeek, final LocalTime localTime) {
        return Arrays.stream(AttendanceStatus.values())
                .sorted((a1, a2) -> a2.limit - a1.limit)
                .filter(status -> localTime.isAfter(calculateBoundaryTest(dayOfWeek, status)))
                .findAny()
                .orElse(ATTENDANCE);
    }

    private static LocalTime calculateBoundaryTest(final DayOfWeek dayOfWeek, final AttendanceStatus attendanceStatus) {
        return ClassTime.findByDayOfWeek(dayOfWeek).plusMinutes(attendanceStatus.limit);
    }

}
