package domain.attendance.constant;

import domain.datetime.CampusDate;
import domain.datetime.CampusTime;
import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
        // TODO : Q5
        Map<AttendanceStatus, CampusTime> status = new LinkedHashMap<>();
        status.put(ABSENCE, CampusTime.of(hourLimit, ABSENCE_LIMIT));
        status.put(TARDINESS, CampusTime.of(hourLimit, TARDINESS_LIMIT));

        for (Map.Entry<AttendanceStatus, CampusTime> entry : status.entrySet()) {
            if (time.isAfter(entry.getValue())) {
                return entry.getKey();
            }
        }
        return ATTENDANCE;
    }

    public String getValue() {
        return value;
    }
}
