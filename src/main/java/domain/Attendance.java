package domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    public static final int MONDAY_START_HOUR = 13;
    public static final int REST_DAY_START_HOUR = 10;
    public static final int ABSENT_LIMIT_MINUTE = 30;
    public static final int LATE_LIMIT_MINUTE = 5;
    public static final int MONDAY = 1;

    private LocalDateTime date;

    public Attendance(LocalDateTime date) {
        this.date = date;
    }

    public AttendanceStatus calculateAttendanceStatus() {
        int dayOfWeek = date.getDayOfWeek().getValue();
        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                REST_DAY_START_HOUR, 0);
        if (dayOfWeek == MONDAY) {
            startDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), MONDAY_START_HOUR, 0);
        }

        Duration duration = Duration.between(startDate, date);
        if (duration.toMinutes() > LATE_LIMIT_MINUTE && duration.toMinutes() <= ABSENT_LIMIT_MINUTE) {
            return AttendanceStatus.LATE;
        }
        if (duration.toMinutes() > ABSENT_LIMIT_MINUTE) {
            return AttendanceStatus.ABSENT;
        }
        return AttendanceStatus.PRESENT;
    }

    public void updateAttendance(Time time) {
        date = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                time.getHour(), time.getMinute());
    }

    public boolean isSameDay(LocalDate specificDate) {
        return date.toLocalDate().isEqual(specificDate);
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public LocalDateTime getDate() {
        return date;
    }
}
