package dto;

import java.time.LocalDate;
import java.time.LocalTime;

import domain.AttendanceStatus;

public record ModifiedResult (
    LocalDate date,
    LocalTime before,
    AttendanceStatus beforeStatus,
    LocalTime after,
    AttendanceStatus afterStatus
){

}
