package attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND(),
    LATE(),
    ABSENCE(),
    ;

    public static AttendanceStatus from(LocalDate localDate, LocalTime time) {
        return null;
    }
}
