package domain;

public class Attendance {

    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;
    private final AttendanceStatus attendanceStatus;

    public Attendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = AttendanceStatus.checkAttendanceStatus(attendanceDate, attendanceTime);
    }
}
