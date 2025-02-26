package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceSystem {
    public LocalDate TODAY = LocalDate.of(2024, 12, 17);
    private final Map<String, AttendanceBook> attendanceBooks;

    public AttendanceSystem() {
        attendanceBooks = new HashMap<>();
    }

    public void attendance(String name, LocalTime time) {
        if (!attendanceBooks.containsKey(name)) {
            attendanceBooks.put(name, new AttendanceBook());
        }
        if (attendanceBooks.get(name).hasAttendanceRecord(TODAY)) {
            throw new IllegalArgumentException();
        }
        attendanceBooks.get(name).attendance(TODAY, time);
    }


    public LocalDateTime getAttendanceRecord(String name, LocalDate date) {
        return attendanceBooks.get(name).getAttendanceDateTimeByDate(date);
    }

    public LocalDateTime getAttendanceRecordToday(String name) {
        return attendanceBooks.get(name).getAttendanceDateTimeByDate(TODAY);
    }

    public void editAttendance(String name, LocalDate date, LocalTime time) {
        attendanceBooks.get(name).attendance(date, time);
    }

    public int getAbsenceCount(String name) {
        return attendanceBooks.get(name).getAbsenceCount(TODAY);
    }

    public int getTardyCount(String name) {
        return attendanceBooks.get(name).getTardyCount(TODAY);
    }

    public int getAttendCount(String name) {
        return attendanceBooks.get(name).getAttendCount(TODAY);

    }

    public RiskStatus getRisk(String name) {
        return attendanceBooks.get(name).getRiskStatus(TODAY);
    }
}
