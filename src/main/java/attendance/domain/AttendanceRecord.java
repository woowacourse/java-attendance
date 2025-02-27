package attendance.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceRecord {
    private final List<AttendanceTime> attendanceRecord;

    public AttendanceRecord() {
        this.attendanceRecord = new ArrayList<>();
    }

    public void addAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceRecord.add(new AttendanceTime(attendanceTime));
    }

    public List<AttendanceTime> getAttendanceRecord() {
        return Collections.unmodifiableList(attendanceRecord);
    }
}
