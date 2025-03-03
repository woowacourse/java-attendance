package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceFileDto  (
    String name, LocalDate attendanceDate, LocalTime attendanceTime
){
    public static AttendanceFileDto of(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        return new AttendanceFileDto(name, attendanceDate, attendanceTime);
    }
}
