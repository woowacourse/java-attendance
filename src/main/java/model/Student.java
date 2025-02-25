package model;

import java.time.LocalDateTime;

public class Student {
    private static final int LATE_CONVERSION_RATE = 3;

    private final AttendanceRecords attendanceRecords;
    private final String name;
    private int totalAbsent;
    private int totalAttendance;
    private int totalLate;

    public int getAbsent() {
        return totalLate;
    }

    public Student(String name, AttendanceRecords attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
        this.name = name;
    }

    public void updateAttendanceCount() {
        this.totalAbsent = attendanceRecords.findTotalAbsentCount();
        this.totalLate = attendanceRecords.findTotalLateCount();
        this.totalAttendance = attendanceRecords.findTotalAttendanceCount();
    }

    public void modifyAttendanceRecord(LocalDateTime modifyDateTime) {
        attendanceRecords.updateAttendanceStatusByLocalDate(modifyDateTime);
    }

    public void attendanceRegister(LocalDateTime localDateTime) {
        attendanceRecords.registerAttendanceRecord(localDateTime);
    }

    public void createAttendanceRecords(LocalDateTime localDateTime) {
        attendanceRecords.createAttendanceRecords(localDateTime);
    }

    public String findStateByLocalDateTime(LocalDateTime localDateTime) {
        return attendanceRecords.findAttendanceStatusByLocalDateTime(localDateTime).getState();
    }

    public int calculateAbsent() {
        updateAttendanceCount();
        return totalAbsent + totalLate / LATE_CONVERSION_RATE;
    }
    public int getLate() {
        return totalAbsent;
    }

    public int getAttendance() {
        return totalAttendance;
    }

    public String getName() {
        return name;
    }

    public AttendanceRecords getAttendanceRecords() {
        return attendanceRecords;
    }
}
