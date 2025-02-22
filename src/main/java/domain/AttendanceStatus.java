package domain;

import static constants.TimeConstants.EXCEPT_MONDAY_ATTEND_TIME_END;
import static constants.TimeConstants.EXCEPT_MONDAY_LATE_TIME_END;
import static constants.TimeConstants.MONDAY_ATTEND_TIME_END;
import static constants.TimeConstants.MONDAY_LATE_TIME_END;
import static constants.TimeConstants.OPERATION_TIME_END;
import static constants.TimeConstants.OPERATION_TIME_START;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND("(출석)"),
    ABSENT("(결석)"),
    LATE("(지각)"),
    NONE("");

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
        if (isTimeInRange(time, OPERATION_TIME_START, MONDAY_ATTEND_TIME_END)) {
            return ATTEND;
        }
        if (isTimeInRange(time, MONDAY_ATTEND_TIME_END, MONDAY_LATE_TIME_END)) {
            return LATE;
        }
        if (isTimeInRange(time, MONDAY_LATE_TIME_END, OPERATION_TIME_END)) {
            return ABSENT;
        }
        return NONE;
    }

    public static AttendanceStatus getExceptMonday(LocalTime time) {
        if (isTimeInRange(time, OPERATION_TIME_START, EXCEPT_MONDAY_ATTEND_TIME_END)) {
            return ATTEND;
        }
        if (isTimeInRange(time, EXCEPT_MONDAY_ATTEND_TIME_END, EXCEPT_MONDAY_LATE_TIME_END)) {
            return LATE;
        }
        if (isTimeInRange(time, EXCEPT_MONDAY_LATE_TIME_END, OPERATION_TIME_END)) {
            return ABSENT;
        }
        return NONE;
    }

    private static boolean isTimeInRange(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return !time.isBefore(startTime) && !time.isAfter(endTime);
    }

    public String getMessage() {
        return message;
    }
}