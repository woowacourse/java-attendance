package dto;

import java.time.LocalDate;
import java.time.LocalTime;

import domain.AttendanceStatus;

public record ModifiedResponseDto(
    LocalDate date,
    InnerStatus before,
    InnerStatus after
){

    public record InnerStatus(
        LocalTime time,
        AttendanceStatus status
    ) {

    }
}
