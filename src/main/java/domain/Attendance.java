package domain;

import java.time.LocalDateTime;

public class Attendance {
    private final LocalDateTime dateAndTime;
    private final AttendanceStatus attendanceStatus;

    public Attendance(LocalDateTime localDateTime) {
        dateAndTime = localDateTime;
        this.attendanceStatus = AttendanceStatus.checkAttendanceState(localDateTime);
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public int getDayOfMonth() {
        return dateAndTime.getDayOfMonth();
    }

    public AttendanceStatus getStatus() {
        return attendanceStatus;
    }

    public String getStatusValue() {
        return attendanceStatus.getStringValue();
    }

    public boolean isEqualDate(LocalDateTime localDateTime) {
        return dateAndTime.toLocalDate().isEqual(localDateTime.toLocalDate());
    }

    public boolean isEqualDayOfMonth(int dayOfMonth) {
        return dateAndTime.getDayOfMonth() == dayOfMonth;
    }
}
