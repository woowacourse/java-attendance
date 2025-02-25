package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceType {
    SAFE("출석"),
    LATE("지각"),
    ABSENT("결석"),
    FREE("자유");

    private static final int HOUR_MONDAY = 13;
    private static final int HOUR_OTHER_WEEKDAY = 10;
    private static final int MINUTE_ALL_WEEKDAY = 0;

    private static final int MINUTE_LATE = 5;
    private static final int MINUTE_ABSENT = 30;

    private final String type;

    AttendanceType(String type) {
        this.type = type;
    }

    public static AttendanceType of(final LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        LocalTime localTime = localDateTime.toLocalTime();

        if (isMonday(dayOfWeek)) {
            return calculateAttendanceType(localTime, HOUR_MONDAY);
        }
        if (isOtherWeekday(dayOfWeek)) {
            return calculateAttendanceType(localTime, HOUR_OTHER_WEEKDAY);
        }
        return FREE;
    }

    private static boolean isMonday(final DayOfWeek dayOfWeek) {
        return dayOfWeek.equals(DayOfWeek.MONDAY);
    }

    private static boolean isOtherWeekday(final DayOfWeek dayOfWeek) {
        return dayOfWeek.equals(DayOfWeek.TUESDAY) ||
                dayOfWeek.equals(DayOfWeek.WEDNESDAY) ||
                dayOfWeek.equals(DayOfWeek.THURSDAY) ||
                dayOfWeek.equals(DayOfWeek.FRIDAY);
    }

    private static AttendanceType calculateAttendanceType(final LocalTime localTime, final int hour) {
        if (localTime.isAfter(LocalTime.of(hour, MINUTE_ALL_WEEKDAY + MINUTE_ABSENT))) {
            return ABSENT;
        }
        if (localTime.isAfter(LocalTime.of(hour, MINUTE_ALL_WEEKDAY + MINUTE_LATE))) {
            return LATE;
        }
        return SAFE;
    }

    @Override
    public String toString() {
        return type;
    }
}
