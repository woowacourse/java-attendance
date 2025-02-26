package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceHistory {

    private final Map<LocalDate, AttendanceRecord> attendanceHistory;

    public AttendanceHistory() {
        this.attendanceHistory = new HashMap<>();
    }

    public AttendanceRecord attendance(final LocalDateTime attendanceDateTime) {
        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
        attendanceHistory.put(attendanceDateTime.toLocalDate(), attendanceRecord);
        return attendanceRecord;
    }
}
