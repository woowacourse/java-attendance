package model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceType {

    출석(Duration.ofMinutes(0)),
    지각(Duration.ofMinutes(5)),
    결석(Duration.ofMinutes(30));

    public static final LocalTime DEFAULT_TIME = LocalTime.MIN;

    AttendanceType(Duration threshold) {
        this.threshold = threshold;
    }

    private final Duration threshold;

    public static AttendanceType from(final LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = LocalTime.from(attendanceDateTime);
        LocalTime startTime = getStartTime(attendanceDateTime.getDayOfWeek());
        return getAttendanceTypeByTime(attendanceTime, startTime);
    }

    private static AttendanceType getAttendanceTypeByTime(final LocalTime attendanceTime, final LocalTime startTime) {
        if (attendanceTime.equals(DEFAULT_TIME) || attendanceTime.isAfter(startTime.plus(결석.threshold))) {
            return 결석;
        }
        if (attendanceTime.isAfter(startTime.plus(지각.threshold))) {
            return 지각;
        }
        return 출석;
    }

    private static LocalTime getStartTime(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return LocalTime.of(13, 0);
        }
        return LocalTime.of(10, 0);
    }
}
