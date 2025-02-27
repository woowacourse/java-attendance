package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE,
    LATE,
    ABSENCE;


    public static AttendanceStatus calculateStatus(final LocalTime time, final DayOfWeek tuesday) {
        return null;
    }
}
