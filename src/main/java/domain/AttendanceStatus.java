package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND("(출석)"),
    ABSENT("(결석)"),
    LATE("(지각)"),
    NONE("");

    public String getMessage() {
        return message;
    }

    AttendanceStatus(String message) {
        this.message = message;
    }

    private final String message;

    public static AttendanceStatus judgeStatus(LocalDate date, LocalTime time) {
        int day = date.getDayOfMonth();
        if (Calendar.isMonday(day)) {
            return getInMonday(time);
        }
        return getExceptMonday(time);
    }

    public static AttendanceStatus getInMonday(LocalTime time) {
        if (!time.isBefore(LocalTime.of(8, 0)) && !time.isAfter(LocalTime.of(13, 5))) {
            return ATTEND;
        }
        if (!time.isBefore(LocalTime.of(13, 5)) && !time.isAfter(LocalTime.of(13, 30))) {
            return LATE;
        }
        if (!time.isBefore(LocalTime.of(13, 30)) && !time.isAfter(LocalTime.of(23, 0))) {
            return ABSENT;
        }
        return NONE;
    }

    public static AttendanceStatus getExceptMonday(LocalTime time) {
        if (!time.isBefore(LocalTime.of(8, 0)) && !time.isAfter(LocalTime.of(10, 5))) {
            return ATTEND;
        }
        if (!time.isBefore(LocalTime.of(10, 5)) && !time.isAfter(LocalTime.of(10, 30))) {
            return LATE;
        }
        if (!time.isBefore(LocalTime.of(10, 30)) && !time.isAfter(LocalTime.of(23, 0))) {
            return ABSENT;
        }
        return NONE;
    }
}