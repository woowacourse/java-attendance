package io.dto;

import domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponse(
        LocalDate attendDate,
        LocalTime attendTime,
        AttendanceStatus status
) {
}
