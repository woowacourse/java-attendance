package dto;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRecord(
        LocalDate date,
        LocalTime time,
        AttendanceStatus status
) {

}
