package controller.dto;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import java.time.LocalTime;

public record AfterAttendanceInfo(
        LocalTime updateTime,
        AttendanceStatus attendanceStatus
) {

    public static AfterAttendanceInfo from(Attendance attendance) {
        AttendanceTime attendanceTime = attendance.getAttendanceTime();
        LocalTime time = LocalTime.of(attendanceTime.hour(), attendanceTime.minute());
        return new AfterAttendanceInfo(time, attendance.getAttendanceStatus());
    }
}
