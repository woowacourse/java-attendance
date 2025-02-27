package model;

import common.Common;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

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
        if (time.equals(Common.noneAttendanceTime)) {
            return ABSENCE;
        }
        if (time.isBefore(LocalTime.of(startTime, 6))) {
            return NORMAL;
        }
        if (time.isBefore(LocalTime.of(startTime, 31))) {
            return LATE;
        }
        return ABSENCE;
    }

    public static List<AttendanceStatus> findAllInAscendingOrder() {
        return Arrays.stream(AttendanceStatus.values()).toList();
    }

    public String getMeaning() {
        return meaning;
    }
}
