package domain;

import static util.parser.DateTimeParser.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceStatus {
    PRESENT("출석", 0),
    LATE("지각", 6),
    ABSENT("결석", 31);

    private final String name;
    private final int boundaryMinute;

    AttendanceStatus(String name, int boundaryMinute) {
        this.name = name;
        this.boundaryMinute = boundaryMinute;
    }

    public static AttendanceStatus of(DayOfWeek dayOfWeek, LocalTime time) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return findStatusOfMonday(time);
        }
        return findStatusOfDefault(time);
    }

    private static AttendanceStatus findStatusOfMonday(LocalTime time) {
        if (time.isBefore(parseIntegerToTime(13, LATE.boundaryMinute))) {
            return PRESENT;
        }
        if (time.isBefore(parseIntegerToTime(13, ABSENT.boundaryMinute))) {
            return LATE;
        }
        return ABSENT;
    }

    private static AttendanceStatus findStatusOfDefault(LocalTime time) {
        if (time.isBefore(parseIntegerToTime(10, LATE.boundaryMinute))) {
            return PRESENT;
        }
        if (time.isBefore(parseIntegerToTime(10, ABSENT.boundaryMinute))) {
            return LATE;
        }
        return ABSENT;
    }
}