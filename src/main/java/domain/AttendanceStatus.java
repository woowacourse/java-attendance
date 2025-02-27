package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    ON_TIME("출석"),
    LATE("지각"),
    ABSENCE("결석"),
    NONE("없음");

    public static final int MONDAY_START_HOUR = 13;
    public static final int NOT_MONDAY_START_HOUR = 10;

    public static final int ON_TIME_LATE_GAP = 5;
    public static final int ON_TIME_ABSENCE_GAP = 30;

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    private static LocalTime getStartTimeByDayOfWeek(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return LocalTime.of(MONDAY_START_HOUR, 0);
        }
        return LocalTime.of(NOT_MONDAY_START_HOUR, 0);
    }

    public static AttendanceStatus determine(LocalDateTime attendTime) {
        AttendanceDateTimeValidator.validate(attendTime);

        LocalDate date = attendTime.toLocalDate();
        LocalTime time = attendTime.toLocalTime();
        return computeAttendanceStatus(date, time);
    }

    private static AttendanceStatus computeAttendanceStatus(LocalDate date, LocalTime time) {
        LocalTime startTime = getStartTimeByDayOfWeek(date.getDayOfWeek());
        LocalTime onTimeThreshold = startTime.plusMinutes(ON_TIME_LATE_GAP);
        LocalTime lateThreshold = startTime.plusMinutes(ON_TIME_ABSENCE_GAP);

        if (time.isBefore(onTimeThreshold) || time.equals(onTimeThreshold)) {
            return ON_TIME;
        }
        if (time.isBefore(lateThreshold) || time.equals(lateThreshold)) {
            return LATE;
        }
        return ABSENCE;
    }


    @Override
    public String toString() {
        return name;
    }
}
