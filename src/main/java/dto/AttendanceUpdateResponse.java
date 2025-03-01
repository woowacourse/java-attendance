package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import model.AttendanceType;

public record AttendanceUpdateResponse(
        LocalDate date,
        LocalTime previousTime,
        AttendanceType previousAttendanceType,
        LocalTime updateTime,
        AttendanceType updateAttendanceType
) {
}
