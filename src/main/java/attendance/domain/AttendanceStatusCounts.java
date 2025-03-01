package attendance.domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class AttendanceStatusCounts {

    private final Map<AttendanceStatus, Long> attendanceStatusCounts;

    public AttendanceStatusCounts() {
        attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);
    }

    public AttendanceStatusCounts(Map<AttendanceStatus, Long> attendanceStatusCounts) {
        this.attendanceStatusCounts = new EnumMap<>(attendanceStatusCounts);
    }

    public Map<AttendanceStatus, Long> getAttendanceStatusCounts() {
        return Collections.unmodifiableMap(attendanceStatusCounts);
    }
}
