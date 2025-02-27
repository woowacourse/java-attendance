package attendance.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecord {
    private final List<LocalDateTime> attendanceRecord;

    public AttendanceRecord() {
        this.attendanceRecord = new ArrayList<>();
    }

    public void addAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceRecord.add(attendanceTime);
    }

    public List<LocalDateTime> getAttendanceRecord() {
        return attendanceRecord;
    }
}
