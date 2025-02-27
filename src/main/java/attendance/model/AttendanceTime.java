package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AttendanceTime {
    private final LocalDate date;
    private final int hour;
    private final int minute;

    public AttendanceTime(final LocalDate date, final int hour, final int minute) {
        validateAttendDate(date);
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }

    private void validateAttendDate(final LocalDate date) {
        if (isWeekend(date.getDayOfWeek())) {
            throw new IllegalArgumentException("[ERROR] 등교 날짜가 아닙니다.");
        }
    }

    private boolean isWeekend(final DayOfWeek day) {
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
