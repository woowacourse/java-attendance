package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND("(출석)"),
    ABSENT("(결석)"),
    LATE("(지각)"),
    NONE("");

    public static final LocalTime OPERATION_HOUR_START = LocalTime.of(8, 0);
    public static final LocalTime OPERATION_HOUR_END = LocalTime.of(23, 0);
    private static final LocalTime MONDAY_ATTEND_TIME_END = LocalTime.of(13, 5);
    private static final LocalTime MONDAY_LATE_TIME_END = LocalTime.of(13, 30);
    private static final LocalTime EXCEPT_MONDAY_ATTEND_TIME_START = LocalTime.of(8, 0);
    private static final LocalTime EXCEPT_MONDAY_ATTEND_TIME_END = LocalTime.of(10, 5);
    private static final LocalTime EXCEPT_MONDAY_LATE_TIME_END = LocalTime.of(10, 30);
    private final String message;

    AttendanceStatus(String message) {
        this.message = message;
    }

    public static AttendanceStatus judgeStatus(LocalDate date, LocalTime time) {
        int day = date.getDayOfMonth();
        if (Calendar.isMonday(day)) {
            return getInMonday(time);
        }
        return getExceptMonday(time);
    }

    public static AttendanceStatus getInMonday(LocalTime time) {
        if (!time.isBefore(OPERATION_HOUR_START) && !time.isAfter(MONDAY_ATTEND_TIME_END)) {
            return ATTEND;
        }
        if (!time.isBefore(MONDAY_ATTEND_TIME_END) && !time.isAfter(MONDAY_LATE_TIME_END)) {
            return LATE;
        }
        if (!time.isBefore(MONDAY_LATE_TIME_END) && !time.isAfter(OPERATION_HOUR_END)) {
            return ABSENT;
        }
        return NONE;
    }

    public static AttendanceStatus getExceptMonday(LocalTime time) {
        if (!time.isBefore(EXCEPT_MONDAY_ATTEND_TIME_START) && !time.isAfter(EXCEPT_MONDAY_ATTEND_TIME_END)) {
            return ATTEND;
        }
        if (!time.isBefore(EXCEPT_MONDAY_ATTEND_TIME_END) && !time.isAfter(EXCEPT_MONDAY_LATE_TIME_END)) {
            return LATE;
        }
        if (!time.isBefore(EXCEPT_MONDAY_LATE_TIME_END) && !time.isAfter(OPERATION_HOUR_END)) {
            return ABSENT;
        }
        return NONE;
    }

    public String getMessage() {
        return message;
    }
}