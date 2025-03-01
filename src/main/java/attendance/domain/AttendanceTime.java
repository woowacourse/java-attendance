package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceTime {
    private final LocalDateTime attendanceTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceTime(final LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = AttendanceStatus.fetchUserAttendanceStatus(attendanceTime);
    }

    public boolean isSameDateTime(final LocalDateTime inputTime) {
        return attendanceTime.getDayOfMonth() == inputTime.getDayOfMonth();
    }

    public boolean isAttendance() {
        return this.attendanceStatus.equals(AttendanceStatus.ATTENDANCE);
    }

    public boolean isLate() {
        return this.attendanceStatus.equals(AttendanceStatus.LATE);
    }

    public boolean isAbsence() {
        return this.attendanceStatus.equals(AttendanceStatus.ABSENCE);
    }

}
