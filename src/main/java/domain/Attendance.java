package domain;

import java.time.LocalDateTime;

public class Attendance {
    private LocalDateTime date;

    public Attendance(LocalDateTime date) {
        this.date = date;
    }

    public AttendanceStatus calculateAttendanceStatus() {
        return AttendanceStatus.calculateAttendanceStatus(date);
    }

    public void updateAttendance(Time time) {
        date = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                time.getHour(), time.getMinute());
    }

    public boolean isSameDay(int date) {
        return this.date.getDayOfMonth() == date;
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public LocalDateTime getDate() {
        return date;
    }
}
