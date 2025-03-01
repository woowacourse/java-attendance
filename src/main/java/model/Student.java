package model;

public class Student {
    private final String name;
    private final StudentAttendanceHistory studentAttendanceHistory;

    public Student(String name, StudentAttendanceHistory studentAttendanceHistory) {
        this.name = name;
        this.studentAttendanceHistory = studentAttendanceHistory;
    }

    public void addAttendanceDateTime(AttendanceDateTime attendanceDateTime) {
        studentAttendanceHistory.addAttendanceDateTime(attendanceDateTime);
    }

    public boolean isExistSameAttendanceDateTime(AttendanceDateTime wantToFindAttendanceDateTime) {
        return studentAttendanceHistory.isExistSameAttendanceDateTime(wantToFindAttendanceDateTime);
    }

    public void modifyAttendanceDateTime(AttendanceDateTime attendanceDateTime) {
        studentAttendanceHistory.modifyAttendance(attendanceDateTime);
    }

    public boolean isSameName(String studentName) {
        return studentName.equals(name);
    }
}
