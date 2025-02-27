package attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;

public record AttendanceResponse(
    LocalDateTime dateTime,
    AttendanceStatus status
) {

    public static AttendanceResponse of(Crew crew, LocalDate date) {
        return new AttendanceResponse(
            LocalDateTime.of(date, crew.getAttendanceTimeOf(date)),
            crew.getAttendanceStatusOf(date)
        );
    }
}
