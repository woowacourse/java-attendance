package attendance.dto;

import attendance.domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRemarkDto (
    LocalDate attendanceDate, LocalTime attendanceTime, AttendanceStatus attendanceStatus
){

    public static AttendanceRemarkDto of (LocalDate attendanceDate, LocalTime attendanceTime, AttendanceStatus attendanceStatus) {
        return new AttendanceRemarkDto(attendanceDate, attendanceTime, attendanceStatus);
    }
}
