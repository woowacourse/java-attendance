package domain;

import java.util.List;

public class AttendanceRecord {
    private final List<Attendance> attendanceRecord;

    public AttendanceRecord(List<Attendance> attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }
}
