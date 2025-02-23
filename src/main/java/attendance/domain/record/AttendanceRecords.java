package attendance.domain.record;

import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceRecords {

    private final List<AttendanceRecord> records = new ArrayList<>();

    public void add(AttendanceRecord record) {
        validateNotSaved(record.getDate());
        if (!record.isExpulsion()) {
            records.add(record);
        }
    }

    public Optional<AttendanceRecord> find(LocalDate date) {
        return records.stream()
                .filter(record -> record.checkSameDate(date))
                .findAny();
    }

    public void remove(LocalDate date) {
        Optional<AttendanceRecord> originRecord = find(date);
        originRecord.ifPresent(record -> records.remove(record));
    }

    public List<AttendanceRecord> findRecordsInMonth(int year, Month month) {
        return records.stream().filter(record -> record.isInMonth(year, month)).toList();
    }

    public int calculateAttendanceRecordCount(LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> inPeriod = records.stream()
                .filter(record -> record.isInPeriod(startDate, endDate)).toList();
        return (int) inPeriod.stream()
                .filter(record -> record.getType() == AttendanceType.ATTENDANCE).count();
    }

    public int calculateLateRecordCount(LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> inPeriod = records.stream()
                .filter(record -> record.isInPeriod(startDate, endDate)).toList();
        return (int) inPeriod.stream()
                .filter(record -> record.getType() == AttendanceType.LATE).count();
    }

    private void validateNotSaved(LocalDate date) {
        Optional<AttendanceRecord> record = find(date);
        if (record.isPresent()) {
            throw new IllegalArgumentException(ExceptionMessage.ALREADY_ATTENDANCE.getContent());
        }
    }
}
