package service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceRegisterResponse(
        LocalDate date,
        Optional<LocalTime> time,
        String status
) {
}
