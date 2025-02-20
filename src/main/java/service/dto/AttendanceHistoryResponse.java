package service.dto;

import domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceHistoryResponse(LocalDate date, Optional<LocalTime> time, AttendanceStatus status) {
}
