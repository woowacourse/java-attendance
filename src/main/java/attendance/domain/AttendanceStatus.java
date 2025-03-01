package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String name;

    private static final int MONDAY_HOUR_LIMIT = 13;
    private static final int GENERAL_HOUR_LIMIT = 10;
    private static final int LATE_LIMIT = 5;
    private static final int ABSENCE_LIMIT = 30;

    AttendanceStatus(final String name) {
        this.name = name;
    }

    public static AttendanceStatus fetchUserAttendanceStatus(final LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        if (dayOfWeek.equals(DayOfWeek.MONDAY)) {
            return findMondayAttendanceStatus(hour, minute);
        }
        return findOtherDayAttendanceStatus(hour, minute);
    }

    private static AttendanceStatus findOtherDayAttendanceStatus(int hour, int minute) {
        return getAttendanceStatus(hour, minute, GENERAL_HOUR_LIMIT);
    }

    private static AttendanceStatus getAttendanceStatus(int hour, int minute, int generalHourLimit) {
        if (hour > generalHourLimit || (hour == generalHourLimit && minute > ABSENCE_LIMIT)
                || hour == 0 && minute == 0) {
            return ABSENCE;
        }
        if (hour == generalHourLimit && minute > LATE_LIMIT) {
            return LATE;
        }
        return ATTENDANCE;
    }

    private static AttendanceStatus findMondayAttendanceStatus(int hour, int minute) {
        return getAttendanceStatus(hour, minute, MONDAY_HOUR_LIMIT);
    }

}
