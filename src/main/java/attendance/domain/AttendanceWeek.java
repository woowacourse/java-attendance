package attendance.domain;

import java.time.DayOfWeek;
import java.util.Arrays;

public enum AttendanceWeek {

    MONDAY(DayOfWeek.MONDAY),
    TUESDAY(DayOfWeek.TUESDAY),
    WEDNESDAY(DayOfWeek.WEDNESDAY),
    THURSDAY(DayOfWeek.THURSDAY),
    FRIDAY(DayOfWeek.FRIDAY),
    SATURDAY(DayOfWeek.SATURDAY),
    SUNDAY(DayOfWeek.SUNDAY);

    private final DayOfWeek week;

    AttendanceWeek(final DayOfWeek week) {
        this.week = week;
    }

    public static AttendanceWeek of(final DayOfWeek inputWeek) {
        return Arrays.stream(AttendanceWeek.values())
                .filter(attendanceWeek -> attendanceWeek.week.equals(inputWeek))
                .findFirst()
                .orElseThrow();
    }
}
