package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Crew {
    public static final int SYSTEM_YEAR = 2024;
    public static final int SYSTEM_MONTH = 12;
    public static final int DECEMBER_DAYS_END = 31;
    public static final int DECEMBER_DAYS_START = 1;

    private final String name;
    private final Map<LocalDate, LocalTime> attendanceRecords = new HashMap<>();

    public Crew(String name) {
        this.name = name;
    }

    public void putAttendanceRecord(LocalDate date, LocalTime localTime) {
        validateNoDuplicateAttendance(date);
        attendanceRecords.put(date, localTime);
    }

    private void validateNoDuplicateAttendance(LocalDate input) {
        if (attendanceRecords.containsKey(input)) {
            throw new IllegalArgumentException(ErrorCode.ATTENDANCE_DATE_DUPLICATED.getMessage());
        }
    }

    public void modifyAttendanceRecord(LocalDate date, LocalTime localTime) {
        validateAttendanceExists(date);
        attendanceRecords.put(date, localTime);
    }

    private void validateAttendanceExists(LocalDate input) {
        if (!attendanceRecords.containsKey(input)) {
            throw new IllegalArgumentException(ErrorCode.ATTENDANCE_DATE_NOT_FOUND.getMessage());
        }
    }

    public int countAttendanceStatusInDecember(AttendanceStatus targetStatus) {
        return (int) IntStream.rangeClosed(DECEMBER_DAYS_START, DECEMBER_DAYS_END)
                .mapToObj(this::getAttendanceStatusByDay)
                .filter(status -> status == targetStatus)
                .count();
    }

    private AttendanceStatus getAttendanceStatusByDay(int day) {
        LocalDate date = LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, day);
        if (attendanceRecords.containsKey(date)) {
            return AttendanceStatus.findByAttendDateAndTime(date, attendanceRecords.get(date));
        }
        return AttendanceStatus.ABSENT;
    }

    public Penalty getPenalty() {
        int lateCount = countAttendanceStatusInDecember(AttendanceStatus.LATE);
        int absentCount = countAttendanceStatusInDecember(AttendanceStatus.ABSENT);
        return Penalty.findPenaltyByAttendanceStatusCount(lateCount, absentCount);
    }

    public boolean hasPenalty(Penalty penalty) {
        return getPenalty() == penalty;
    }

    public boolean hasName(String input) {
        return name.equals(input);
    }

    public LocalTime findTimeByDate(LocalDate date) {
        return attendanceRecords.get(date);
    }

    public boolean hasAttendanceRecordWithDate(LocalDate date) {
        return attendanceRecords.containsKey(date);
    }

    public String getName() {
        return name;
    }
}
