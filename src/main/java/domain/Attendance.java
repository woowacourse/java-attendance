package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private LocalDateTime dateTime;
    private AttendanceStatus status;

    public Attendance(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.status = AttendanceStatus.getStatusByAttendedTime(dateTime);
    }

    public LocalDate getLocalDate() {
        return dateTime.toLocalDate();
    }
    public LocalTime getLocalTime() {
        return dateTime.toLocalTime();
    }

    public int getDayOfMonth() {
        return dateTime.getDayOfMonth();
    }

    public AttendanceStatus getAttendanceStatus() {
        return status;
    }

    public ModifyResult changeTimeTo(LocalTime time) {
        Attendance oldAttendance = new Attendance(dateTime);
        dateTime = LocalDateTime.of(dateTime.toLocalDate(), time);
        status = AttendanceStatus.getStatusByAttendedTime(dateTime);
        return new ModifyResult(oldAttendance, this);
    }
}
