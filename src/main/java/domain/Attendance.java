package domain;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(date.getDayOfMonth(), that.date.getDayOfMonth());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date.getDayOfMonth());
    }
}
