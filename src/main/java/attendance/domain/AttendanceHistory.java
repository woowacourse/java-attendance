package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_CHECK_ATTENDANCE_AGAIN;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                .anyMatch(existingRecord -> existingRecord.getWoowaDate()
                        .equals(record.getWoowaDate()));
    }

    public void modifyRecord(WoowaDate targetDate, LocalTime modifyTime) {
        Optional<AttendanceRecord> findRecord = findRecordByDate(targetDate);
        AttendanceRecord record = findRecord.orElseGet(() ->
                new AttendanceRecord(targetDate, modifyTime)
        );
        addRecord(record);
        record.modify(modifyTime);
    }

    public Optional<AttendanceRecord> findRecordByDate(WoowaDate targetDate) {
        return records.stream()
                .filter(record -> record.isSameDate(targetDate))
                .findAny();
    }

    public AttendanceReport toReport(LocalDate startDate, LocalDate endDate, EducationDayPolicy policy) {
        return new AttendanceReport(this, startDate, endDate, policy);
    }

    public List<AttendanceRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

}
