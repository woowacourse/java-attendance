package domain.attendance.constant;

import domain.datetime.CampusDate;
import domain.datetime.CampusTime;
import java.time.DayOfWeek;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private final static int MONDAY_HOUR_LIMIT = 13;
    private final static int WEEKDAY_HOUR_LIMIT = 10;
    private final static int ABSENCE_LIMIT = 30;
    private final static int TARDINESS_LIMIT = 5;

    private final String value;

    AttendanceStatus(String value) {
        this.value = value;
    }

    public static AttendanceStatus calculateByDateAndTime(final CampusDate date, final CampusTime time) {
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return calculateStatus(MONDAY_HOUR_LIMIT, time);
        }
        return calculateStatus(WEEKDAY_HOUR_LIMIT, time);
    }

    private static AttendanceStatus calculateStatus(final int hourLimit, final CampusTime time) {
        CampusTime absenceLimit = CampusTime.of(hourLimit, ABSENCE_LIMIT);
        CampusTime tardinessLimit = CampusTime.of(hourLimit, TARDINESS_LIMIT);

        if (time.isAfter(absenceLimit)) {
            return AttendanceStatus.ABSENCE;
        }
        if (time.isAfter(tardinessLimit)) {
            return AttendanceStatus.TARDINESS;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    public String getValue() {
        return value;
    }
}
