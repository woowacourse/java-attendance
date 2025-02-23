package dto;

import java.time.LocalDate;
import java.time.LocalTime;

import domain.AttendanceStatus;

public record HistoryDto(
    LocalDate date,
    LocalTime time,
    AttendanceStatus status,
    boolean isChecked
) {

}
