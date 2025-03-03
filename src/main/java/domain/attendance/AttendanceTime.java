package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceTime {
    MONDAY(DayOfWeek.MONDAY, 13, 5),
    TUESDAY(DayOfWeek.TUESDAY, 10, 5),
    WEDNESDAY(DayOfWeek.WEDNESDAY, 10, 5),
    THURSDAY(DayOfWeek.THURSDAY, 10, 5),
    FRIDAY(DayOfWeek.FRIDAY, 10, 5),
    ;
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    private static final int TARDY_MINUTE = 25;

    private final DayOfWeek dayOfWeek;
    private final int hour;
    private final int minute;

    AttendanceTime(DayOfWeek dayOfWeek, int hour, int minute) {
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
    }

    public static boolean isOperatingTime(LocalDateTime dateTime) {
        return (START_TIME.equals(dateTime.toLocalTime()) || START_TIME.isBefore(dateTime.toLocalTime())) &&
                (END_TIME.equals(dateTime.toLocalTime()) || END_TIME.isAfter(dateTime.toLocalTime()));
    }

    public static boolean isAttendance(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return Arrays.stream(values())
                .filter(value -> dayOfWeek.equals(value.dayOfWeek))
                .findFirst()
                .filter(value -> value.hour >= dateTime.getHour() && value.minute >= dateTime.getMinute())
                .isPresent();
    }

    public static boolean isTardy(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return Arrays.stream(values())
                .filter(value -> dayOfWeek.equals(value.dayOfWeek))
                .findFirst()
                .filter(value -> value.hour >= dateTime.getHour()
                        && value.minute + TARDY_MINUTE >= dateTime.getMinute())
                .isPresent();
    }
}
