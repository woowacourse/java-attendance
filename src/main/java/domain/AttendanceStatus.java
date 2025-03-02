package domain;

import static util.parser.DateTimeParser.parseIntegerToTime;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

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
        return Optional.ofNullable(time)
            .map(t -> {
                if (dayOfWeek == DayOfWeek.MONDAY) {
                    return findStatusOfMonday(t);
                }
                return findStatusOfDefault(t);
            })
            .orElse(ABSENT);
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

    public String getName() {
        return name;
    }
}