package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TreeSet;

public class AttendanceRecords {
    private final TreeSet<AttendanceRecord> records = new TreeSet<>();

    public void add(AttendanceRecord record) {
        if (records.contains(record)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하였습니다." + System.lineSeparator());
        }
        records.add(record);
    }

    public void remove(AttendanceRecord record) {
        records.remove(record);
    }

    public void update(LocalDate oldDate, LocalTime newTime) {
        AttendanceRecord oldRecord = getRecordOnDate(oldDate);
        AttendanceRecord newRecord = new AttendanceRecord(LocalDateTime.of(oldDate, newTime));
        remove(oldRecord);
        add(newRecord);
    }

    public AttendanceRecord getRecordOnDate(LocalDate date) {
        return records.stream()
                .filter(record -> record.getDate().equals(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 출석 기록이 없습니다." + System.lineSeparator()));
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
