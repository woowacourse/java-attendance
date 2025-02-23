package domain;

import java.time.LocalDate;
import java.util.TreeSet;

public class AttendanceRecords {
    private static final LocalDate FILL_START_DATE = LocalDate.of(2024, 11, 30);

    private final TreeSet<AttendanceRecord> attendanceRecords;

    public AttendanceRecords() {
        this.attendanceRecords = new TreeSet<>();
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
            fillAbsence(date);
        }
    }

    public AttendanceRecord removeRecord(LocalDate date) {
        AttendanceRecord attendanceRecord = attendanceRecords.stream()
                .filter(record -> record.getDate().equals(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없는 날짜는 수정할 수 없습니다.\n"));
        attendanceRecords.remove(attendanceRecord);
        return attendanceRecord;
    }

    public int getTardyCount() {
        return (int) attendanceRecords.stream()
                .filter(AttendanceRecord::isTardy)
                .count();
    }

    public int getAbsentCount() {
        return (int) attendanceRecords.stream()
                .filter(AttendanceRecord::isAbsent)
                .count();
    }

    public int getAttendanceCount(Attendance targetAttendance) {
        return (int) attendanceRecords.stream()
                .filter(attendanceRecord -> attendanceRecord.getAttendance().equals(targetAttendance))
                .count();
    }

    public DisciplinaryStatus getDisciplinaryStatus() {
        int absentCount = getAbsentCount();
        int tardyCount = getTardyCount();
        return DisciplinaryStatus.getStatus(absentCount, tardyCount);
    }

    public TreeSet<AttendanceRecord> getAttendanceRecords() {
        return attendanceRecords;
    }

    private void fillAbsence(LocalDate date) {
        if (!hasRecordOfDate(date) && !Day.checkHoliday(date)) {
            this.attendanceRecords.add(AttendanceRecord.asAbsent(date));
        }
    }
}
