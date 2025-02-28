package attendance.domain;

import java.util.List;

public record AttendanceRecords(
        List<AttendanceRecord> records
) {

    public AttendanceRecords(final List<AttendanceRecord> records) {
        this.records = records.stream()
                .sorted()
                .toList();
    }
}
