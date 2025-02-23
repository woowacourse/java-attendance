package model;

import java.time.LocalTime;

public class AttendanceRecord {
    private LocalTime attendanceTime;
    private AttendanceStatus attendanceStatus;

    public AttendanceRecord(LocalTime attendanceTime, AttendanceStatus attendanceStatus) {
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = attendanceStatus;
    }

    public AttendanceRecord(AttendanceRecord other) {
        this.attendanceTime = other.attendanceTime;
        this.attendanceStatus = other.attendanceStatus;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }
}
