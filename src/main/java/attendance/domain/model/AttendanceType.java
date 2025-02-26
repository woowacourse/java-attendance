package attendance.domain.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceType {

    ATTENDANCE("출석", Duration.ofMinutes(0)),
    LATE("지각", Duration.ofMinutes(5)),
    ABSENCE("결석", Duration.ofMinutes(30));

    public static final LocalTime DEFAULT_TIME = LocalTime.MIN;
    private static final int MONDAY_START_HOUR = 13;
    private static final int EXCEPT_MONDAY_START_HOUR = 10;

    private final String name;
    private final Duration threshold;

    AttendanceType(final String name, final Duration threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public static AttendanceType from(final LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = LocalTime.from(attendanceDateTime);
        LocalTime startTime = getStartTime(attendanceDateTime.getDayOfWeek());
        return getAttendanceTypeByTime(attendanceTime, startTime);
    }

    private static AttendanceType getAttendanceTypeByTime(final LocalTime attendanceTime, final LocalTime startTime) {
        if (attendanceTime.equals(DEFAULT_TIME) || attendanceTime.isAfter(startTime.plus(ABSENCE.threshold))) {
            return ABSENCE;
        }
        if (attendanceTime.isAfter(startTime.plus(LATE.threshold))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    private static LocalTime getStartTime(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return LocalTime.of(MONDAY_START_HOUR, 0);
        }
        return LocalTime.of(EXCEPT_MONDAY_START_HOUR, 0);
    }

    public String getName() {
        return name;
    }
}
