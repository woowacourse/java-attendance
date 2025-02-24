package domain;

import java.time.LocalTime;

public record CrewAttendance(LocalTime attendanceTime, AttendanceStatus attendanceStatus) {

}

