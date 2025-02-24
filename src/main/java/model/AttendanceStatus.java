package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    NORMAL("출석"),
    LATE("지각"),
    ABSENCE("결석")
    ;

    private final String meaning;

    AttendanceStatus(String meaning) {
        this.meaning = meaning;
    }

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

    public String getMeaning() {
        return meaning;
    }
}
