package attendance.domain;

import java.time.LocalTime;

import attendance.dto.AttendanceTimeDto;

public record Attendance(AttendanceStatus attendanceStatus,LocalTime time) {

}
