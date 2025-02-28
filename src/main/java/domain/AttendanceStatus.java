package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석"),
    NONE("없음");

    public static final LocalTime MONDAY_ATTEND_TIME = LocalTime.of(13, 0);
    public static final LocalTime EXCEPT_MONDAY_ATTEND_TIME = LocalTime.of(10, 0);
    public static final int LATE_THRESHOLD_MINUTES = 5;
    public static final int ABSENT_THRESHOLD_MINUTES = 30;

    private final String message;

    AttendanceStatus(String message) {
        this.message = message;
    }

    public static String findMessageByAttendDateAndTime(LocalDate date, LocalTime time) {
        return findByAttendDateAndTime(date, time).message;
    }

    public static AttendanceStatus findByAttendDateAndTime(LocalDate date, LocalTime time) {
        if (DayType.matches(date, DayType.MONDAYS)) {
            return findByAttendTime(MONDAY_ATTEND_TIME, time);
        }
        return findByAttendTime(EXCEPT_MONDAY_ATTEND_TIME, time);
    }

    private static AttendanceStatus findByAttendTime(LocalTime standardTime, LocalTime attendTime) {
        if (isBetween(
                attendTime,
                standardTime,
                standardTime.plusMinutes(LATE_THRESHOLD_MINUTES))) {
            return ATTEND;
        }
        if (isBetween(
                attendTime,
                standardTime.plusMinutes(LATE_THRESHOLD_MINUTES),
                standardTime.plusMinutes(ABSENT_THRESHOLD_MINUTES))) {
            return LATE;
        }
        if (!attendTime.isBefore(
                standardTime.plusMinutes(ABSENT_THRESHOLD_MINUTES))) {
            return ABSENT;
        }
        return NONE;
    }

    private static boolean isBetween(LocalTime target, LocalTime start, LocalTime end) {
        return !target.isBefore(start) && !target.isAfter(end);
    }

    public String getMessage() {
        return message;
    }
}
