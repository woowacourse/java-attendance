package domain;

import java.time.LocalDate;
import java.util.TreeSet;

public class AttendanceRecords {
    private final TreeSet<AttendanceRecord> records = new TreeSet<>();

    public void add(AttendanceRecord record) {
        if (records.contains(record)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하였습니다." + System.lineSeparator());
        }
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
