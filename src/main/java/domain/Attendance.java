package domain;

public class Attendance {


    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;
    private final AttendanceStatus status;

    public Attendance(final AttendanceDate attendanceDate, final AttendanceTime attendanceTime,
                      final AttendanceStatus status) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.status = status;
    }

}
