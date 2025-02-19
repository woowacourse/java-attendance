package service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceHistoryResponse(LocalDate date, Optional<LocalTime> time, String status) {
}
