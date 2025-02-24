package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student {
    private final AttendanceRecords attendanceRecords;
    private final String name;
    private int totalAbsent;
    private int totalAttendance;
    private int totalLate;

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

    public int getAbsent() {
        return totalLate;
    }

    public Student(String name, AttendanceRecords attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
        this.name = name;
    }

    public void updateAttendanceCount(){
        this.totalAbsent = attendanceRecords.findTotalAbsentCount();
        this.totalLate = attendanceRecords.findTotalLateCount();
        this.totalAttendance = attendanceRecords.findTotalAttendanceCount();
    }

    public void attendanceRegister(LocalDateTime localDateTime){
        attendanceRecords.registerAttendanceRecord(localDateTime);
    }

    public void createAttendanceRecords(LocalDateTime localDateTime){
        attendanceRecords.createAttendanceRecords(localDateTime);
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return LocalDate.from(localDateTime1).equals(LocalDate.from(localDateTime2));
    }

    public String findStateByLocalDateTime(LocalDateTime localDateTime) {
        return attendanceRecords.findAttendanceStatusByLocalDateTime(localDateTime).getState();
    }

    public int calculateAbsent() {
        updateAttendanceCount();
        return totalAbsent + totalLate/3;
    }

}
