package controller.dto;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import java.time.LocalDate;
import java.time.LocalTime;

public record BeforeAttendanceInfo(
        LocalDate updateDate,
        LocalTime beforeTime,
        AttendanceStatus attendanceStatus
) {

    public static BeforeAttendanceInfo from(Attendance attendance) {
        AttendanceTime attendanceTime = attendance.getAttendanceTime();
        LocalTime time = LocalTime.of(attendanceTime.hour(), attendanceTime.minute());
        return new BeforeAttendanceInfo(attendance.getAttendanceDate(), time, attendance.getAttendanceStatus());
    }
}
