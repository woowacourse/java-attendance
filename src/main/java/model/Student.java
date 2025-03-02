package model;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private final String name;
    private final StudentAttendanceHistory studentAttendanceHistory;

    public Student(String name, StudentAttendanceHistory studentAttendanceHistoryMap) {
        this.name = name;
        this.studentAttendanceHistory = studentAttendanceHistoryMap;
    }

    public void addAttendanceDateTime(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        studentAttendanceHistory.addStudentAttendanceHistory(attendanceDate, attendanceTime);
    }

    public void modifyAttendanceDateTime(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        studentAttendanceHistory.modifyStudentAttendanceHistory(attendanceDate, attendanceTime);
    }

    public void isAlreadyExistAttendanceDate(AttendanceDate attendanceDate) {
        if (studentAttendanceHistory.isExistSameAttendanceDate(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석한 요일입니다. 다시 출석하고 싶으면 수정 기능을 이용해 주세요.");
        }
    }

    public void validateAttendanceBeforeModification(AttendanceDate attendanceDate) {
        if (!studentAttendanceHistory.isExistSameAttendanceDate(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 출석하지 않은 요일입니다. 수정하고 싶으면 출석을 먼저 진행해 주세요.");
        }
    }

    public boolean isSameName(String studentName) {
        return studentName.equals(name);
    }

    public AttendanceTime findAttendanceTimeByAttendanceDate(AttendanceDate attendanceDate) {
        return studentAttendanceHistory.findAttendanceTimeByAttendanceDate(attendanceDate);
    }

    public void updateMissingAttendanceRecords(AttendanceDate attendanceStartDate, AttendanceDate today) {
        studentAttendanceHistory.updateMissingAttendanceRecords(attendanceStartDate, today);
    }

    public Map<AttendanceStatus, Integer> calculateStudentAttendanceResult() {
        Map<AttendanceStatus, Integer> attendanceCountMap = new HashMap<>();
        studentAttendanceHistory.calculateStudentAttendanceResult(attendanceCountMap);
        return attendanceCountMap;
    }

    public StudentAttendanceHistory getStudentAttendanceHistory() {
        return studentAttendanceHistory;
    }
}
