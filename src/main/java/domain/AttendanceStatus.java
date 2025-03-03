package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import util.OutputParser;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석"),
    NONE("없음");

    public static final LocalTime MONDAY_ATTEND_TIME = LocalTime.of(13, 0);
    public static final LocalTime EXCEPT_MONDAY_ATTEND_TIME = LocalTime.of(10, 0);
    public static final int LATE_THRESHOLD_MINUTES = 5;
    public static final int ABSENT_THRESHOLD_MINUTES = 30;
    public static final LocalTime CAMPUS_OPERATION_HOUR_START = LocalTime.of(8, 0);
    public static final LocalTime CAMPUS_OPERATION_HOUR_END = LocalTime.of(23, 0);

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
                CAMPUS_OPERATION_HOUR_START,
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

    public static void validateIsOperationHour(LocalTime time) {
        if (!isOperationHour(time)) {
            throw new IllegalArgumentException(
                    ErrorCode.TIME_NOT_OPERATION_HOUR.getFormattedMessage(OutputParser.parseTimeToString(time)));
        }
    }

    private static boolean isOperationHour(LocalTime time) {
        return isBetween(time, CAMPUS_OPERATION_HOUR_START, CAMPUS_OPERATION_HOUR_END);
    }

    public String getMessage() {
        return message;
    }
}
