package domain;

import java.time.LocalDate;

public class Attendance {

    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;
    private final AttendanceStatus attendanceStatus;

    public Attendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = AttendanceStatus.checkAttendanceStatus(attendanceDate, attendanceTime);
    }

    public boolean isSameDate(LocalDate date) {
        return attendanceDate.isSameAs(date);
    }

    public boolean isLate() {
        return this.attendanceStatus.equals(AttendanceStatus.LATE);
    }

    public boolean isUnattendedOrNoShow() {
        return this.attendanceStatus.equals(AttendanceStatus.UNATTENDED) || this.attendanceStatus.equals(AttendanceStatus.NO_SHOW);
    }
}
