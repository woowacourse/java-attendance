package domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private LocalDateTime dateTime;
    private AttendanceStatus status;

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

    public void changeTimeTo(LocalTime time) {
        dateTime = LocalDateTime.of(dateTime.toLocalDate(), time);
        status = AttendanceStatus.getStatusByAttendedTime(dateTime);
    }
}
