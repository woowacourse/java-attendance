package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceTime {
    private final LocalDateTime attendanceTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceTime(final LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = AttendanceStatus.fetchUserAttendanceStatus(attendanceTime);
    }

    public boolean isAbsence() {
        return this.attendanceStatus.equals(AttendanceStatus.ABSENCE);
    }

}
