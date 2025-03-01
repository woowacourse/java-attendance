package domain;

public class Attendance {

    private final AttendanceDate attendanceDate;
    private AttendanceTime attendanceTime;
    private AttendanceStatus attendanceStatus;

    public Attendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = AttendanceStatus.checkAttendanceStatus(attendanceDate, attendanceTime);
    }

    public boolean isSameDate(AttendanceDate date) {
        return attendanceDate.isSameAs(date);
    }

    public boolean isAttended() {
        return this.attendanceStatus.equals(AttendanceStatus.ATTEND);
    }

    public boolean isLate() {
        return this.attendanceStatus.equals(AttendanceStatus.LATE);
    }

    public boolean isUnattendedOrNoShow() {
        return this.attendanceStatus.equals(AttendanceStatus.UNATTENDED) || this.attendanceStatus.equals(AttendanceStatus.NO_SHOW);
    }

    public void editTime(AttendanceTime newTime) {
        this.attendanceTime = newTime;
        this.attendanceStatus = AttendanceStatus.checkAttendanceStatus(this.attendanceDate, newTime);
    }

    public AttendanceDate getAttendanceDate() {
        return this.attendanceDate;
    }

    public AttendanceTime getAttendanceTime() {
        return this.attendanceTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return this.attendanceStatus;
    }
}
