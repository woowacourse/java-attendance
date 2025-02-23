package domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Day {

    private static final Integer STANDARD_LATE_MINUTE = 5;
    private static final Integer STANDARD_ABSENT_MINUTE = 30;

    private final LocalDate date;

    public Day(LocalDate date) {
        this.date = date;
    }

    public Boolean isEqualTo(LocalDate date) {
        return this.date.equals(date);
    }

    public Boolean checkHoliday() {
        return Holiday.isHoliday(date) || getDayOfWeek().equals(DayOfWeek.SATURDAY) || getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    public boolean isLate(LocalTime attendanceTime) {
        LocalTime standardTime = getStandardTime();
        if (standardTime == null || attendanceTime == null) {
            return false;
        }
        long betweenMinutes = Duration.between(standardTime, attendanceTime).toMinutes();
        return betweenMinutes > STANDARD_LATE_MINUTE && betweenMinutes <= STANDARD_ABSENT_MINUTE;
    }

    public boolean isAbsent(LocalTime attendanceTime) {
        LocalTime standardTime = getStandardTime();
        if (standardTime == null) {
            return false;
        }

        if (attendanceTime == null) {
            return true;
        }
        long betweenMinutes = Duration.between(standardTime, attendanceTime).toMinutes();
        return betweenMinutes > STANDARD_ABSENT_MINUTE;
    }

    public LocalDate getDate() {
        return date;
    }

    private LocalTime getStandardTime() {
        return getDayOfWeek().getStandardTime();
    }

    private DayOfWeek getDayOfWeek() {
        return DayOfWeek.getInstance(date);
    }
}
