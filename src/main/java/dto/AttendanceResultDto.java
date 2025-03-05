package dto;

import domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResultDto(
        LocalDate localDate,
        LocalTime localTime,
        AttendanceStatus attendanceStatus
) {

}
