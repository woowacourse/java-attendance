package model.attendance;

import common.Campus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public enum AttendanceStatus {
    NORMAL("출석", 6),
    LATE("지각", 31),
    ABSENCE("결석", -1)
    ;

    private static final int START_TIME_EXCEPT_MONDAY = 10;
    private static final int START_TIME_ON_MONDAY = 13;

    private final String meaning;
    private final int upperBoundTime;

    AttendanceStatus(String meaning, int upperBoundTime) {
        this.meaning = meaning;
        this.upperBoundTime = upperBoundTime;
    }

    public static AttendanceStatus findByAttendanceTime(LocalDate date, LocalTime time) {
        int startTime = START_TIME_EXCEPT_MONDAY;
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = START_TIME_ON_MONDAY;
        }
        if (time.equals(Campus.NONE_ATTENDANCE_TIME)) {
            return ABSENCE;
        }
        if (time.isBefore(LocalTime.of(startTime, NORMAL.upperBoundTime))) {
            return NORMAL;
        }
        if (time.isBefore(LocalTime.of(startTime, LATE.upperBoundTime))) {
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
