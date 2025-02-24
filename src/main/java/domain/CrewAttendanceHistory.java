package domain;

import java.time.LocalTime;

public record CrewAttendanceHistory(LocalTime attendanceTime, AttendanceStatus attendanceStatus) {
}
