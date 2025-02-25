package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceHistory {
    private final List<AttendanceRecord> records = new ArrayList<>();

    public void addRecord(AttendanceRecord record) {
        records.add(record);
    }

    public void modifyRecord(LocalDate targetDate, LocalTime modifyTime) {
        AttendanceRecord record = getRecordByDate(targetDate);
        record.modify(modifyTime);
    }

    public AttendanceRecord getRecordByDate(LocalDate targetDate) {
        return records.stream()
                .filter(record -> record.isSameDate(targetDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 없는 날짜입니다."));
    }

    public WarningStatus getWarningStatus() {
        long lateCount = calculateLateCount();
        long absentCount = calculateAbsentCount();
        return WarningStatus.from(absentCount, lateCount);
    }

    private long calculateLateCount() {
        return records.stream()
                .filter(record -> record.getAttendanceStatus() == AttendanceStatus.LATE)
                .count();
    }

    private long calculateAbsentCount() {
        return records.stream()
                .filter(record -> record.getAttendanceStatus() == AttendanceStatus.ABSENT)
                .count();
    }

    public List<AttendanceRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

}
