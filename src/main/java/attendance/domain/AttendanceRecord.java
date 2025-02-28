package attendance.domain;

import java.time.LocalDate;
import java.util.List;

public class AttendanceRecord {

    private final List<Attendance> record;

    public AttendanceRecord(final List<Attendance> record) {
        this.record = record;
    }

    public List<Attendance> getRecordExcludingToday() {
        return record.stream()
                .filter(attendance -> !attendance.isSameDate(LocalDate.now()))
                .toList();
    }

    public List<Attendance> getRecord() {
        return record;
    }
}
