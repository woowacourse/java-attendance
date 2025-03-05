package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Crew {
    public static final int SYSTEM_YEAR = 2024;
    public static final int SYSTEM_MONTH = 12;
    public static final int DECEMBER_DAYS_END = 31;
    public static final int DECEMBER_DAYS_START = 1;

    private final String name;
    private final AttendanceRecords attendanceRecords;

    public Crew(String name) {
        this.name = name;
        this.attendanceRecords = new AttendanceRecords();
    }

    public void putAttendanceRecord(LocalDate date, LocalTime localTime) {
        attendanceRecords.putAttendanceRecord(date, localTime);
    }

    public void modifyAttendanceRecord(LocalDate date, LocalTime localTime) {
        attendanceRecords.modifyAttendanceRecord(date, localTime);
    }

    public int countAttendanceStatusInDecember(AttendanceStatus targetStatus) {
        return attendanceRecords.countAttendanceStatusInDecember(targetStatus);
    }

    public Penalty getPenalty() {
        return attendanceRecords.getPenalty();
    }

    public boolean hasPenalty(Penalty penalty) {
        return attendanceRecords.getPenalty() == penalty;
    }

    public boolean hasName(String input) {
        return name.equals(input);
    }

    public LocalTime findTimeByDate(LocalDate date) {
        return attendanceRecords.findTimeByDate(date);
    }

    public boolean hasAttendanceRecordWithDate(LocalDate date) {
        return attendanceRecords.hasAttendanceRecordWithDate(date);
    }

    public String getName() {
        return name;
    }
}