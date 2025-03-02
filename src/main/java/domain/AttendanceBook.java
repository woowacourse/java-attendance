package domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AttendanceBook {
    private final Map<AttendanceDate, AttendanceTime> attendanceBook;
    private final AttendanceStatuses attendanceStatuses;

    public AttendanceBook() {
        attendanceBook = new HashMap<>();
        attendanceStatuses = new AttendanceStatuses();
    }

    public AttendanceBook(Map<AttendanceDate, AttendanceTime> attendanceBook, Map<AttendanceDate, AttendanceStatus> attendanceStatuses) {
        this.attendanceBook = attendanceBook;
        this.attendanceStatuses = new AttendanceStatuses(attendanceStatuses);
    }

    public Optional<AttendanceTime> getAttendanceTimeByDate(AttendanceDate date) {
        return Optional.ofNullable(attendanceBook.get(date));
    }

    public void attendance(AttendanceDate date, AttendanceTime time) {
        attendanceBook.put(date, time);
        attendanceStatuses.put(date, time);
    }

    public boolean hasAttendanceRecord(AttendanceDate date) {
        return attendanceBook.containsKey(date);
    }

    public int getAbsenceCount() {
        return attendanceStatuses.getAbsenceCount();
    }

    public int getAttendCount() {
        return attendanceStatuses.getAttendCount();
    }

    public int getTardyCount() {
        return attendanceStatuses.getTardyCount();
    }


    public RiskStatus getRiskStatus() {
        return attendanceStatuses.getRiskStatus();
    }


    public AttendanceStatus getAttendanceStatus(AttendanceDate date) {
        return attendanceStatuses.get(date);
    }

    public Map<AttendanceDate, AttendanceTime> getAttendanceBook() {
        return Collections.unmodifiableMap(attendanceBook);
    }

    public AttendanceStatuses getAttendanceStatuses() {
        return attendanceStatuses;
    }
}
