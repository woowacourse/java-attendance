package domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceState {

    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private static final LocalTime OTHER_DAY_LATE_TIME = LocalTime.of(10, 5);
    private static final LocalTime OTHER_DAY_ABSENCE_TIME = LocalTime.of(10, 30);
    private static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 5);
    private static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 30);

    private final String state;

    AttendanceState(final String state) {
        this.state = state;
    }

    public static AttendanceState findStateBy(final LocalDateTime time) {
        if (Calender.isMonday(time)) {
            return getMondayState(time);
        }
        return getOtherDayState(time);
    }

    private static AttendanceState getMondayState(final LocalDateTime time) {
        if (isMondayLate(time)) {
            return ABSENCE;
        }
        if (isMondayAbsence(time)) {
            return LATE;
        }
        return ATTENDANCE;
    }

    private static AttendanceState getOtherDayState(final LocalDateTime time) {
        if (isOtherDayLate(time)) {
            return ABSENCE;
        }
        if (isOtherDayAbsence(time)) {
            return LATE;
        }
        return ATTENDANCE;
    }

    private static boolean isMondayAbsence(final LocalDateTime time) {
        return time.toLocalTime().isAfter(MONDAY_LATE_TIME);
    }

    private static boolean isMondayLate(final LocalDateTime time) {
        return time.toLocalTime().isAfter(MONDAY_ABSENCE_TIME);
    }

    private static boolean isOtherDayLate(final LocalDateTime time) {
        return time.toLocalTime().isAfter(OTHER_DAY_ABSENCE_TIME);
    }

    private static boolean isOtherDayAbsence(final LocalDateTime time) {
        return time.toLocalTime().isAfter(OTHER_DAY_LATE_TIME);
    }
}
