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
        if (!time.isBefore(OPERATION_TIME_START) && !time.isAfter(MONDAY_ATTEND_TIME_END)) {
            return ATTEND;
        }
        if (!time.isBefore(MONDAY_ATTEND_TIME_END) && !time.isAfter(MONDAY_LATE_TIME_END)) {
            return LATE;
        }
        if (!time.isBefore(MONDAY_LATE_TIME_END) && !time.isAfter(OPERATION_TIME_END)) {
            return ABSENT;
        }
        return NONE;
    }

    public static AttendanceStatus getExceptMonday(LocalTime time) {
        if (!time.isBefore(OPERATION_TIME_START) && !time.isAfter(EXCEPT_MONDAY_ATTEND_TIME_END)) {
            return ATTEND;
        }
        if (!time.isBefore(EXCEPT_MONDAY_ATTEND_TIME_END) && !time.isAfter(EXCEPT_MONDAY_LATE_TIME_END)) {
            return LATE;
        }
        if (!time.isBefore(EXCEPT_MONDAY_LATE_TIME_END) && !time.isAfter(OPERATION_TIME_END)) {
            return ABSENT;
        }
        return NONE;
    }

    public String getMessage() {
        return message;
    }
}