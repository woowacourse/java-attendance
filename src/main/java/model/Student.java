package model;

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

    public boolean isSameName(String studentName) {
        return studentName.equals(name);
    }

    public AttendanceTime findAttendanceTimeByAttendanceDate(AttendanceDate attendanceDate) {
        return studentAttendanceHistory.findAttendanceTimeByAttendanceDate(attendanceDate);
    }

    public void updateMissingAttendanceRecords(AttendanceDate attendanceStartDate, AttendanceDate today) {
        studentAttendanceHistory.updateMissingAttendanceRecords(attendanceStartDate, today);
    }
    public StudentAttendanceHistory getStudentAttendanceHistory() {
        return studentAttendanceHistory;
    }
}
