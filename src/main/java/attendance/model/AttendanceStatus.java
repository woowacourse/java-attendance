package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND(),
    LATE(),
    ABSENCE(),
    ;

    public static AttendanceStatus from(LocalDate localDate, LocalTime time) {
        if (localDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            if (time.isAfter(LocalTime.of(13, 30))) {
                return AttendanceStatus.ABSENCE;
            }
            if (time.isAfter(LocalTime.of(13, 5))) {
                return AttendanceStatus.LATE;
            }
            return AttendanceStatus.ATTEND;
        } else {
            if (time.isAfter(LocalTime.of(10, 30))) {
                return AttendanceStatus.ABSENCE;
            }
            if (time.isAfter(LocalTime.of(10, 5))) {
                return AttendanceStatus.LATE;
            }
            return AttendanceStatus.ATTEND;
        }
    }
}
