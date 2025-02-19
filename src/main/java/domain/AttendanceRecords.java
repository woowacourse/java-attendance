package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AttendanceRecords {
    private static final LocalDate FILL_START_DATE = LocalDate.of(2024, 11, 30);

    private List<AttendanceRecord> attendanceRecords;

    public AttendanceRecords() {
        this.attendanceRecords = new ArrayList<>();
    }

    public void addRecord(AttendanceRecord attendanceRecord) {
        attendanceRecords.add(attendanceRecord);
    }

    public boolean hasRecordOfDate(LocalDate date) {
        return attendanceRecords.stream()
                .anyMatch((record) -> record.getDate().equals(date));
    }

    public void fillAbsences(DateGenerator dateGenerator) {
        for (LocalDate date = dateGenerator.generate().minusDays(1); date.isAfter(FILL_START_DATE); date = date.minusDays(1)) {
            if (!hasRecordOfDate(date) && !Day.checkHoliday(date)) {
                this.attendanceRecords.add(new AttendanceRecord(date));
            }
        }
    }

    public AttendanceRecord removeRecord(LocalDate date) {
        AttendanceRecord attendanceRecord = attendanceRecords.stream()
                .filter(record -> record.getDate().equals(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없는 날짜는 수정할 수 없습니다."));
        attendanceRecords.remove(attendanceRecord);
        return attendanceRecord;
    }

    public int getPresentCount() {
        return (int) attendanceRecords.stream().filter(AttendanceRecord::isPresent).count();
    }

    public int getTardyCount() {
        return (int) attendanceRecords.stream().filter(AttendanceRecord::isTardy).count();
    }

    public int getAbsentCount() {
        return (int) attendanceRecords.stream().filter(AttendanceRecord::isAbsent).count();
    }

    public List<AttendanceRecord> getSortedRecords() {
        return attendanceRecords.stream().sorted(Comparator.comparing(AttendanceRecord::getDate)).toList();
    }
}
