package attendance.dto;

import java.time.LocalTime;

import attendance.domain.AttendanceStatus;

public record AttendanceTimeDto(LocalTime time, AttendanceStatus attendanceStatus) {

}
