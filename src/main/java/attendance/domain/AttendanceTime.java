package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceTime {
    private final LocalDateTime attendanceTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceTime(LocalDateTime inputTime) {
        this.attendanceTime = inputTime;
        this.attendanceStatus = AttendanceStatus.fetchUserAttendanceStatus(inputTime);
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

    public AttendanceTime modifyAttendanceTime(LocalDateTime inputTime) {
        return new AttendanceTime(inputTime);
    }
}
