package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class AttendanceRecords {
    private final Map<LocalDate, LocalTime> records = new HashMap<>();

    public void putAttendanceRecord(LocalDate date, LocalTime localTime) {
        validateNoDuplicateAttendance(date);
        records.put(date, localTime);
    }

    public void modifyAttendanceRecord(LocalDate date, LocalTime localTime) {
        validateAttendanceExists(date);
        records.put(date, localTime);
    }

    public int countAttendanceStatusInDecember(AttendanceStatus targetStatus) {
        return (int) IntStream.rangeClosed(Crew.DECEMBER_DAYS_START, Crew.DECEMBER_DAYS_END)
                .filter(day -> DayType.checkIsWorkingDay(LocalDate.of(Crew.SYSTEM_YEAR, Crew.SYSTEM_MONTH, day)))
                .mapToObj(day -> getAttendanceStatusByDay(LocalDate.of(Crew.SYSTEM_YEAR, Crew.SYSTEM_MONTH, day)))
                .filter(status -> status == targetStatus)
                .count();
    }

    public Penalty getPenalty() {
        int lateCount = countAttendanceStatusInDecember(AttendanceStatus.LATE);
        int absentCount = countAttendanceStatusInDecember(AttendanceStatus.ABSENT);
        return Penalty.findPenaltyByAttendanceStatusCount(lateCount, absentCount);
    }

    public AttendanceStatus getAttendanceStatusByDay(LocalDate date) {
        if (records.containsKey(date)) {
            return AttendanceStatus.findByAttendDateAndTime(date, records.get(date));
        }
        return AttendanceStatus.ABSENT;
    }

    public LocalTime findTimeByDate(LocalDate date) {
        return records.get(date);
    }

    public boolean hasAttendanceRecordWithDate(LocalDate date) {
        return records.containsKey(date);
    }

    private void validateNoDuplicateAttendance(LocalDate date) {
        if (records.containsKey(date)) {
            throw new IllegalArgumentException(ErrorCode.ATTENDANCE_DATE_DUPLICATED.getMessage());
        }
    }

    private void validateAttendanceExists(LocalDate date) {
        if (!records.containsKey(date)) {
            throw new IllegalArgumentException(ErrorCode.ATTENDANCE_DATE_NOT_FOUND.getMessage());
        }
    }
}