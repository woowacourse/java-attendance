package attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import attendance.domain.AttendanceStatus;

public record AttendanceDateDto(LocalDateTime time, AttendanceStatus attendanceStatus) {

    public static AttendanceDateDto generateAttendanceDateDto(LocalDate date, AttendanceTimeDto attendanceTimeDto) {
        LocalDateTime time = LocalDateTime.of(date, attendanceTimeDto.time());
        return new AttendanceDateDto(time, attendanceTimeDto.attendanceStatus());
    }
}
