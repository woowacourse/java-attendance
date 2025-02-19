package domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class Attendance {
    private LocalDateTime date;

    public Attendance(LocalDateTime date) {
        this.date = date;
    }

    public AttendanceStatus calculateAttendanceStatus() {
        int dayOfWeek = date.getDayOfWeek().getValue();
        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 10, 0);
        if (dayOfWeek == 1) {
            startDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 13, 0);
        }

        Duration duration = Duration.between(startDate, date);
        if (duration.toMinutes() > 5 && duration.toMinutes() <= 30) {
            return AttendanceStatus.LATE;
        }
        if (duration.toMinutes() > 30) {
            return AttendanceStatus.ABSENT;
        }
        return AttendanceStatus.PRESENT;
    }

    public void updateAttendance(LocalDateTime updateTime) {
        date = updateTime;
    }

    public boolean isSameDay(int day) {
        return date.getDayOfMonth() == day;
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public LocalDateTime getDate() {
        return date;
    }
}
