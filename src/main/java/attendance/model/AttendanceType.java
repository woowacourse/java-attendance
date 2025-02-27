package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceType {
    EXTRA("추가근무"),
    PRESENT("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String type;

    AttendanceType(final String type) {
        this.type = type;
    }

    public static AttendanceType of(final LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();

        if (isMonday(dayOfWeek)) {
            return calculateType(dateTime.toLocalTime(), "13:00");
        }
        if (isOtherWorkDay(dayOfWeek)) {
            return calculateType(dateTime.toLocalTime(), "10:00");
        }
        return EXTRA;
    }

    private static boolean isMonday(final DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.MONDAY;
    }

    private static boolean isOtherWorkDay(final DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.TUESDAY
                || dayOfWeek == DayOfWeek.WEDNESDAY
                || dayOfWeek == DayOfWeek.THURSDAY
                || dayOfWeek == DayOfWeek.FRIDAY;
    }

    private static AttendanceType calculateType(final LocalTime localTime, final String time) {
        if (localTime.isAfter(LocalTime.parse(time).plusMinutes(30))) {
            return ABSENT;
        }
        if (localTime.isAfter(LocalTime.parse(time).plusMinutes(5))) {
            return LATE;
        }
        return PRESENT;
    }

    @Override
    public String toString() {
        return type;
    }
}
