package dto;

import java.time.LocalDate;
import java.time.LocalTime;

import domain.AttendanceStatus;

public record ModifiedResponseDto(
    LocalDate date,
    InnerModifiedDetail before,
    InnerModifiedDetail after
){

    public record InnerModifiedDetail(
        LocalTime time,
        AttendanceStatus status
    ) {

    }
}
