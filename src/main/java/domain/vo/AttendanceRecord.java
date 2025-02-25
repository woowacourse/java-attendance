package domain.vo;

import domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceRecord(
        LocalDate attendDate,
        Optional<LocalTime> attendTime,
        AttendanceStatus status
) {
}
