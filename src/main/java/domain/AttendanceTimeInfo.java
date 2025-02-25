package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceTimeInfo {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

    private static final LocalTime LATE_TIME = LocalTime.of(0, 5);
    private static final LocalTime ABSENCE_TIME = LocalTime.of(0, 30);

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;

    AttendanceTimeInfo(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    private static AttendanceTimeInfo getAttendanceTimeInfo(DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
                .filter(attendanceTimeInfo -> attendanceTimeInfo.dayOfWeek == dayOfWeek)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("휴일은 출석할 수 없습니다."));
    }

    public static LocalTime getLateLocalTime(DayOfWeek dayOfWeek) {
        return getAttendanceTimeInfo(dayOfWeek).startTime.plusMinutes(LATE_TIME.getMinute());
    }

    public static LocalTime getAbsenceTime(DayOfWeek dayOfWeek) {
        return getAttendanceTimeInfo(dayOfWeek).startTime.plusMinutes(ABSENCE_TIME.getMinute());
    }
}
