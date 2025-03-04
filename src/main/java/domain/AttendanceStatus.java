package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import util.parser.DateTimeParser;

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

    public static Map<AttendanceStatus, Integer> countStatus(Map<LocalDate, DailyRecord> records) {
        Map<AttendanceStatus, Integer> statisticsResult = new LinkedHashMap<>();
        statisticsResult.put(PRESENT, 0);
        statisticsResult.put(LATE, 0);
        statisticsResult.put(ABSENT, 0);

        for (DailyRecord record : records.values()) {
            AttendanceStatus status = record.getStatus();
            statisticsResult.put(status, (statisticsResult.get(status) + 1));
        }
        return statisticsResult;
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
        if (time.equals(LocalTime.MIN)) {
            return ABSENT;
        }
        if (time.isBefore(DateTimeParser.parseIntegerToTime(13, LATE.boundaryMinute))) {
            return PRESENT;
        }
        if (time.isBefore(DateTimeParser.parseIntegerToTime(13, ABSENT.boundaryMinute))) {
            return LATE;
        }
        return ABSENT;
    }

    private static AttendanceStatus findStatusOfDefault(LocalTime time) {
        if (time.equals(LocalTime.MIN)) {
            return ABSENT;
        }
        if (time.isBefore(DateTimeParser.parseIntegerToTime(10, LATE.boundaryMinute))) {
            return PRESENT;
        }
        if (time.isBefore(DateTimeParser.parseIntegerToTime(10, ABSENT.boundaryMinute))) {
            return LATE;
        }
        return ABSENT;
    }

    public String getName() {
        return name;
    }
}