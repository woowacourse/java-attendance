package controller.dto;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResult(
        LocalDate attendanceDate,
        LocalTime attendanceTime,
        AttendanceStatus attendanceStatus
) {

    public static AttendanceResult from(Attendance attendance) {
        AttendanceTime attendanceTime = attendance.getAttendanceTime();
        if (attendanceTime == null) {
            return new AttendanceResult(attendance.getAttendanceDate(), null, attendance.getAttendanceStatus());
        }
        LocalTime time = LocalTime.of(attendanceTime.hour(), attendanceTime.minute());
        return new AttendanceResult(attendance.getAttendanceDate(), time, attendance.getAttendanceStatus());
    }
}
