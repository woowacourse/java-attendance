package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_CHECK_ATTENDANCE_AGAIN;
import static attendance.error.ErrorMessage.ERROR_NO_RECORD_DATE;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceHistory {
    private final List<AttendanceRecord> records = new ArrayList<>();

    public void addRecord(AttendanceRecord record) {
        if (alreadyPresent(record)) {
            throw new IllegalArgumentException(ERROR_CHECK_ATTENDANCE_AGAIN);
        }
        records.add(record);
    }

    private boolean alreadyPresent(AttendanceRecord record) {
        return records.stream()
                .anyMatch(existingRecord -> existingRecord.getDate()
                        .equals(record.getDate()));
    }

    public void modifyRecord(WoowaDate targetDate, LocalTime modifyTime) {
        AttendanceRecord record = getRecordByDate(targetDate);
        record.modify(modifyTime);
    }

    public AttendanceRecord getRecordByDate(WoowaDate targetDate) {
        return records.stream()
                .filter(record -> record.isSameDate(targetDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_NO_RECORD_DATE));
    }

    public WarningStatus getWarningStatus() {
        long lateCount = countByAttendanceStatus(AttendanceStatus.LATE);
        long absentCount = countByAttendanceStatus(AttendanceStatus.ABSENT);
        return WarningStatus.from(absentCount, lateCount);
    }

    public long countByAttendanceStatus(AttendanceStatus status) {
        return records.stream()
                .filter(record -> record.getAttendanceStatus() == status)
                .count();
    }

    public List<AttendanceRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

}
