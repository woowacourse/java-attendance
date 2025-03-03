package domain;

import java.util.Map;

public record AttendanceStatusCount(Map<AttendanceStatus, Long> attendanceStatusCount) {
    public long getCount(AttendanceStatus attendanceStatus) {
        return attendanceStatusCount.getOrDefault(attendanceStatus, 0L);
    }
}

