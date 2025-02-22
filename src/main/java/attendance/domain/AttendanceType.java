package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceType {
    SAFE("출석"),
    LATE("지각"),
    ABSENT("결석"),
    FREE("자유출근");

    private final String type;

    AttendanceType(String type) {
        this.type = type;
    }

    public static AttendanceType of(final LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        LocalTime localTime = localDateTime.toLocalTime();

        if (isMonday(dayOfWeek)) {
            return calculateAttendanceType(localTime, 13, 0);
        }
        if (isOtherWeekday(dayOfWeek)) {
            return calculateAttendanceType(localTime, 10, 0);
        }
        return FREE;
    }

    private static AttendanceType calculateAttendanceType(final LocalTime localTime, final int hour, final int minute) {
        if (localTime.isAfter(LocalTime.of(hour, minute + 30))) {
            return ABSENT;
        }
        if (localTime.isAfter(LocalTime.of(hour, minute + 5))) {
            return LATE;
        }
        return SAFE;
    }

    private static boolean isMonday(DayOfWeek dayOfWeek) {
        return dayOfWeek.equals(DayOfWeek.MONDAY);
    }

    private static boolean isOtherWeekday(final DayOfWeek dayOfWeek) {
        return dayOfWeek.equals(DayOfWeek.TUESDAY) ||
                dayOfWeek.equals(DayOfWeek.WEDNESDAY) ||
                dayOfWeek.equals(DayOfWeek.THURSDAY) ||
                dayOfWeek.equals(DayOfWeek.FRIDAY);
    }

    @Override
    public String toString() {
        return type;
    }
}
