package dto;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record ModifiedResult(
        LocalDate date,
        TimeAttendanceStatus before,
        TimeAttendanceStatus after
) {

    public record TimeAttendanceStatus(
            LocalTime time,
            AttendanceStatus status
    ) {

    }
}
