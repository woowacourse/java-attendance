package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Attendance {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime dateTime;

    public Attendance(final String time) {
        this.dateTime = LocalDateTime.parse(time, FORMATTER);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AttendanceStatus calculateStatus() {
        if (dateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return AttendanceStatus.of(dateTime, 13, 0);
        }
        return AttendanceStatus.of(dateTime, 10, 0);

    }
}
