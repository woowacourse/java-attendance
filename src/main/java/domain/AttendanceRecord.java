package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRecord(
        LocalDate date,
        LocalTime time,
        AttendanceStatus status
) {

}
