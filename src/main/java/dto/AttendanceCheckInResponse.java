package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import model.AttendanceType;

public record AttendanceCheckInResponse(
        LocalDate checkInDate,
        LocalTime checkInTime,
        AttendanceType attendanceType
) {
}
