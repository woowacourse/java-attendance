package attendance.domain;

import java.time.LocalTime;

import attendance.dto.AttendanceTimeDto;

public class Attendance {
    private final AttendanceStatus attendanceStatus;
    private LocalTime time;

    Attendance(LocalTime time, AttendanceStatus attendanceStatus) {
        this.time = time;
        this.attendanceStatus = attendanceStatus;
    }

    public AttendanceTimeDto getAttendanceResult() {
        return new AttendanceTimeDto(time, attendanceStatus);
    }
}
