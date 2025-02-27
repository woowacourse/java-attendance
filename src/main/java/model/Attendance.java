package model;

public class Attendance {

    private final AttendanceDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;

    private Attendance(final AttendanceDateTime attendanceDateTime, final AttendanceStatus attendanceStatus) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = attendanceStatus;
    }

    public static Attendance of(final AttendanceDateTime attendanceDateTime) {
        final AttendanceStatus attendanceStatus = AttendanceStatus.findByAttendanceDateTime(attendanceDateTime);
        return new Attendance(attendanceDateTime, attendanceStatus);
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }
}
