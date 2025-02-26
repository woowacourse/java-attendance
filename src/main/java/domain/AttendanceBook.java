package domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<CrewName, AttendanceRecord> value;

    public AttendanceBook() {
        this.value = new HashMap<>();
    }

    public void addAttendance(CrewName crewName, Attendance attendance) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(attendance);
        value.put(crewName, attendanceRecord);
    }

    public AttendanceRecord findAttendanceRecordBy(CrewName crewName) {
        return value.get(crewName);
    }
}
