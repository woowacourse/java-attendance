package domain;

import java.time.Duration;
import java.time.LocalDateTime;

public enum AttendanceStatus {
    PRESENT(0, 5, "출석"),
    LATE(5, 30, "지각"),
    ABSENT(30, 0, "결석");

    private static final int MONDAY_START_HOUR = 13;
    private static final int REST_DAY_START_HOUR = 10;
    private static final int ABSENT_LIMIT_MINUTE = 30;
    private static final int LATE_LIMIT_MINUTE = 5;
    private static final int MONDAY = 1;

    final int lowerBound;
    final int upperBound;
    final String name;

    AttendanceStatus(int lowerBound, int upperBound, String name) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.name = name;
    }

    public static AttendanceStatus calculateAttendanceStatus(LocalDateTime date) {
        LocalDateTime startTime = calculateStartTime(date, date.getDayOfWeek().getValue());
        Duration duration = Duration.between(startTime, date);
        if (duration.toMinutes() > LATE_LIMIT_MINUTE && duration.toMinutes() <= ABSENT_LIMIT_MINUTE) {
            return LATE;
        }
        if (duration.toMinutes() > ABSENT_LIMIT_MINUTE) {
            return ABSENT;
        }
        return PRESENT;
    }

    private static LocalDateTime calculateStartTime(LocalDateTime date, int dayOfWeek) {
        if (dayOfWeek == MONDAY) {
            return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), MONDAY_START_HOUR, 0);
        }
        return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                REST_DAY_START_HOUR, 0);
    }

    public String getName() {
        return name;
    }
}
