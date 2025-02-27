package attendance.dto;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AttendanceResultResponse(
        LocalDateTime dateTime,
        AttendanceStatus status
) {
    public static AttendanceResultResponse from(final Attendance attendance) {
        LocalDate date = attendance.getAttendanceDate().date();
        LocalTime time = attendance.getAttendanceTime().time();
        return new AttendanceResultResponse(LocalDateTime.of(date, time), attendance.checkAttendanceStatus());
    }
}
