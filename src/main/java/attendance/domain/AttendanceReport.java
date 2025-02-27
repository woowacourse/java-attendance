package attendance.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class AttendanceReport {
    private final List<AttendanceRecord> records;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final EducationDayPolicy policy;

    public AttendanceReport(AttendanceHistory history, LocalDate startDate, LocalDate endDate,
                            EducationDayPolicy policy) {
        this.records = history.getRecords();
        this.startDate = startDate;
        this.endDate = endDate;
        this.policy = policy;
    }

    public WarningStatus getWarningStatus() {
        long lateCount = countLate();
        long absentCount = countAbsent();
        return WarningStatus.from(absentCount, lateCount);
    }

    public long countPresent() {
        return countByAttendanceStatus(AttendanceStatus.PRESENT);
    }

    public long countLate() {
        return countByAttendanceStatus(AttendanceStatus.LATE);
    }

    public long countEffectiveAbsences() {
        return countAbsent() + countLate() / 3;
    }

    public long countAbsent() {
        long absentCount = countByAttendanceStatus(AttendanceStatus.ABSENT);
        long noAttendanceCount = calculateNoAttendanceDays(startDate, endDate, policy);
        return noAttendanceCount + absentCount;
    }

    private long calculateNoAttendanceDays(LocalDate startDate, LocalDate endDate, EducationDayPolicy policy) {
        long educationDayCount = 0;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (policy.isEducationDay(date)) {
                educationDayCount++;
            }
        }

        long attendanceDays = records.size();
        return educationDayCount - attendanceDays;
    }

    private long countByAttendanceStatus(AttendanceStatus status) {
        return records.stream()
                .filter(record -> record.getAttendanceStatus() == status)
                .count();
    }

    public List<WoowaDate> getNoAttendanceDates() {
        return startDate.datesUntil(endDate.plusDays(1))
                .filter(policy::isEducationDay)
                .map(date -> new WoowaDate(date, policy))
                .filter(currentDate -> records.stream().noneMatch(record -> record.getWoowaDate().equals(currentDate)))
                .toList();
    }

    public List<AttendanceRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }
}
