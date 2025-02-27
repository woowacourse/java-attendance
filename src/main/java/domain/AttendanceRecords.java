package domain;

import java.time.LocalDate;
import java.util.TreeSet;

public class AttendanceRecords {
    private final TreeSet<AttendanceRecord> records = new TreeSet<>();

    public void add(AttendanceRecord record) {
        records.add(record);
    }

    public boolean hasRecordOnDate(LocalDate date) {
        return records.stream().anyMatch(record -> record.getDate().equals(date));
    }

    public int getAttendanceCount(AttendanceStatus status) {
        return (int) records.stream()
                .filter(record -> record.getAttendanceStatus() == status)
                .count();
    }
}
