package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student {
    private AttendanceRecords attendanceRecords;
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

    public Student(String name) {
        this.name = name;
    }

    public void updateAttendanceRecords(LocalDateTime updateDateTime) {
        attendanceRecords.updateAttendanceStatusByLocalDate(updateDateTime);
        AttendanceStatus newAttendanceStatus = attendanceRecords.
                findAttendanceStatusFromRecordsByLocalDate(LocalDate.from(updateDateTime));
        AttendanceStatus oldAttendanceStatus = AttendanceRuleByDay.calculateAttendance(updateDateTime);
        decrementAttendance(oldAttendanceStatus);
        incrementAttendance(newAttendanceStatus);
    }

    private void incrementAttendance(AttendanceStatus newAttendanceStatus) {
        if (newAttendanceStatus.equals(AttendanceStatus.ATTENDANCE)){
            totalAttendance++;
        }
        if (newAttendanceStatus.equals(AttendanceStatus.LATE)){
            totalLate++;
        }
        if (newAttendanceStatus.equals(AttendanceStatus.ABSENT)){
            totalAbsent++;
        }
    }

    private void decrementAttendance(AttendanceStatus oldAttendanceStatus) {
        if (oldAttendanceStatus.equals(AttendanceStatus.ATTENDANCE)){
            totalAttendance--;
        }
        if (oldAttendanceStatus.equals(AttendanceStatus.LATE)){
            totalLate--;
        }
        if (oldAttendanceStatus.equals(AttendanceStatus.ABSENT)){
            totalAbsent--;
        }
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return LocalDate.from(localDateTime1).equals(LocalDate.from(localDateTime2));
    }

    public LocalDateTime findLocalDateTime(LocalDateTime localDateTime) {
        for (LocalDateTime localDateTime1 : record.keySet()) {
            if (compareDayIsSame(localDateTime1,localDateTime)) {
                return localDateTime1;
            }
        }
        return null;
    }

    public String findStateByLocalDateTime(LocalDateTime localDateTime) {
        for (LocalDateTime localDateTime1 : record.keySet()) {
            if (compareDayIsSame(localDateTime1,localDateTime)) {
                return record.get(localDateTime1).getState();
            }
        }
        return null;
    }

    public int calculateAbsent() {
        return totalAbsent + totalLate/3;
    }

}
