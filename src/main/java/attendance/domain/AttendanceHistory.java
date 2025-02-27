package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_CHECK_ATTENDANCE_AGAIN;
import static attendance.error.ErrorMessage.ERROR_NO_RECORD_DATE;

import java.time.LocalDate;
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

    public AttendanceReport toReport(LocalDate startDate, LocalDate endDate, EducationDayPolicy policy) {
        return new AttendanceReport(this, startDate, endDate, policy);
    }

    public List<AttendanceRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

}
