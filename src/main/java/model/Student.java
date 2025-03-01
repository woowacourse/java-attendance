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

    public boolean isExistAttendanceDate(AttendanceDate attendanceDate) {
        return studentAttendanceHistory.isExistSameAttendanceDate(attendanceDate);
    }

    public boolean isSameName(String studentName) {
        return studentName.equals(name);
    }

    public StudentAttendanceHistory getStudentAttendanceHistory() {
        return studentAttendanceHistory;
    }
}
