package attendance.domain;

import java.util.List;

public class AttendanceRecords {
    private final List<AttendanceRecord> records;

    public AttendanceRecords(final List<AttendanceRecord> records) {
        this.records = records.stream()
                .sorted()
                .toList();
    }

    public List<AttendanceRecord> getRecords() {
        return records;
    }
}
