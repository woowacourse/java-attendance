package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.TreeSet;

public class AttendanceRecords {
    private final TreeSet<AttendanceRecord> records = new TreeSet<>();

    public void add(AttendanceRecord record) {
        records.add(record);
    }

    public void update(LocalDate date, LocalTime newTime) {
        AttendanceRecord oldRecord = getRecordOnDate(date);
        AttendanceRecord newRecord = new AttendanceRecord(LocalDateTime.of(date, newTime));
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

    public List<AttendanceRecord> getRecordsUntilBefore(LocalDate currentDate) {
        return records.stream()
                .filter(record -> record.getDate().isBefore(currentDate))
                .toList();
    }

    public WarningStatus getWarningStatus() {
        int tardyCount = getAttendanceCount(AttendanceStatus.TARDY);
        int absentCount = getAttendanceCount(AttendanceStatus.ABSENT);
        return WarningStatus.getStatus(tardyCount, absentCount);
    }

    public int getConvertedAbsences() {
        int tardyCount = getAttendanceCount(AttendanceStatus.TARDY);
        int absentCount = getAttendanceCount(AttendanceStatus.ABSENT);
        return WarningStatus.convertTardiesToAbsences(tardyCount, absentCount);
    }

    public int getTardiesAfterConversion() {
        int tardyCount = getAttendanceCount(AttendanceStatus.TARDY);
        return WarningStatus.getTardiesAfterConversion(tardyCount);
    }

    private void remove(AttendanceRecord record) {
        records.remove(record);
    }
}
