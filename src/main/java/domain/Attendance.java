package domain;

import java.time.LocalDateTime;

public class Attendance {
    private final LocalDateTime dateTime;
    private final AttendanceStatus status;

    public Attendance(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.status = AttendanceStatus.getStatusByAttendedTime(dateTime);
    }

    public int getDayOfMonth() {
        return dateTime.getDayOfMonth();
    }

    public AttendanceStatus getAttendanceStatus() {
        return status;
    }
}
