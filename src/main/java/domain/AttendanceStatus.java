package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceStatus {

    ATTENDANCE(1,0),
    LATE(2, 5),
    ABSENCE(3,30);


    private final int code;
    private final int limit;

    AttendanceStatus(final int code, final int limit) {
        this.code = code;
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

    public int getCode() {
        return code;
    }
}
