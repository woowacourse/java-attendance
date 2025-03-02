package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceRecordDto(
        LocalDate attendanceDate,
        Optional<LocalTime> attendanceTime,
        String attendanceStatus
) {
}
