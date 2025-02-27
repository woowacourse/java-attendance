package attendance.dto;

import attendance.domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceInfoDto(
    LocalDate attendanceDate, LocalTime attendanceTime, AttendanceStatus attendanceStatus
){

    public static AttendanceInfoDto of (LocalDate attendanceDate, LocalTime attendanceTime, AttendanceStatus attendanceStatus) {
        return new AttendanceInfoDto(attendanceDate, attendanceTime, attendanceStatus);
    }
}
