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

    public boolean isAbsence() {
        return this.attendanceStatus.equals(AttendanceStatus.ABSENCE);
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

}
