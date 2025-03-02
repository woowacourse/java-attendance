package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecord {
    private final List<AttendanceDate> attendanceDates;

    public AttendanceRecord() {
        attendanceDates = new ArrayList<>();
    }

    public AttendanceRecord(LocalDateTime dateTime) {
        attendanceDates = new ArrayList<>();
        addAttendanceDate(dateTime);
    }

    public void addAttendanceDate(LocalDateTime dateTime) {
        attendanceDates.add(new AttendanceDate(dateTime));
    }

    public void addAttendanceDate(LocalDate date, LocalTime time) {
        attendanceDates.add(new AttendanceDate(date, time));
    }

    public void editAttendanceDate(LocalDate date, LocalTime time) {
        int index = attendanceDates.indexOf(getAttendanceDate(date));
        attendanceDates.set(index, new AttendanceDate(date, time));
    }

    public boolean hasAttendanceDate(LocalDate date) {
        return getAttendanceDate(date) != null;
    }

    public AttendanceDate getAttendanceDate(LocalDate date) {
        return attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.isSameDate(date))
                .findFirst()
                .orElse(null);
    }

    public List<AttendanceDate> getAttendanceDates() {
        return new ArrayList<>(attendanceDates);
    }

    public int calculateTardyCount() {
        return (int) attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.getStatus().equals(AttendanceStatus.TARDY))
                .count();
    }

    public int calculateAbsenceCount() {
        return (int) attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.getStatus().equals(AttendanceStatus.ABSENCE))
                .count();
    }

    public void validateBeforeAdd(LocalDate date) {
        if (hasAttendanceDate(date)) {
            throw new IllegalArgumentException("이미 출석하여, 다시 출석할 수 없습니다. 수정 기능을 이용하십시오.");
        }
    }

    public void validateBeforeEdit(LocalDate date) {
        if (!hasAttendanceDate(date)) {
            throw new IllegalArgumentException("출석 기록이 없어 수정할 수 없습니다.");
        }
    }
}
