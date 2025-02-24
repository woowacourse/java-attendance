package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    NORMAL,
    LATE,
    ABSENCE
    ;

    public static AttendanceStatus findByAttendanceTime(LocalDate date, LocalTime time) {
        int startTime = 10;
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = 13;
        }
        if (time.isBefore(LocalTime.of(startTime, 6))) {
            return NORMAL;
        }
        if (time.isBefore(LocalTime.of(startTime, 31))) {
            return LATE;
        }
        return ABSENCE;
    }
}
