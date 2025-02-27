package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final LocalDate date;
    private final LocalTime time;
    private final AttendanceStatus status;

    public Attendance(final LocalDate date, final LocalTime time, final AttendanceStatus status) {
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public boolean isDateEquals(final LocalDate date) {
        return this.date.isEqual(date);
    }

    public boolean isYearMonthEquals(final LocalDate today) {
        return date.getYear() == today.getYear() &&
                date.getMonthValue() == today.getMonthValue();
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
