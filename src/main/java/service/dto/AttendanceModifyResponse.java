package service.dto;

import domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceModifyResponse(
        LocalDate date,
        Optional<LocalTime> beforeTime,
        String beforeStatus,
        Optional<LocalTime> afterTime,
        String afterStatus
) {
}
