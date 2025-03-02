package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, AttendanceRecord> attendance;

    public AttendanceBook() {
        attendance = new HashMap<>();
    }

    public void addAttendance(String name, LocalDateTime dateTime) {
        attendance.put(name, new AttendanceRecord(dateTime));
    }

    public boolean hasCrew(String name) {
        return attendance.containsKey(name);
    }

    public AttendanceRecord findAttendanceRecordByName(String name) {
        return attendance.get(name);
    }

    public AttendanceDate findAttendanceDateByNameAndDate(String name, LocalDate date) {
        return findAttendanceRecordByName(name).getAttendanceDate(date);
    }

    public boolean hasAttendanceDate(String name, LocalDate date) {
        return findAttendanceRecordByName(name).hasAttendanceDate(date);
    }

    public void attend(String name, LocalDate date, LocalTime time) {
        findAttendanceRecordByName(name).addAttendanceDate(date , time);
    }

    public void edit(String name, LocalDate date, LocalTime time) {
        findAttendanceRecordByName(name).editAttendanceDate(date, time);
    }

    public void validateHasCrew(String name) {
        if (!hasCrew(name)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public void validateBeforeAdd(String name, LocalDate date) {
        findAttendanceRecordByName(name).validateBeforeAdd(date);
    }

    public void validateBeforeEdit(String name, LocalDate date) {
        findAttendanceRecordByName(name).validateBeforeEdit(date);
    }
}
