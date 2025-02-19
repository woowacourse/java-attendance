package domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Day {

    private LocalDate date;
    private DayOfWeek dayOfWeek;

    public Day(LocalDate date) {
        this.date = date;
        this.dayOfWeek = DayOfWeek.getInstance(date);
    }

    public Boolean isEqualTo(LocalDate date) {
        return this.date.equals(date);
    }

    public Boolean checkHoliday() {
        if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            return true;
        }
        return false;
    }

    public boolean isLate(LocalTime attendanceTime) {
        long betweenMinutes = Duration.between(dayOfWeek.getStandardTime(), attendanceTime).toMinutes();
        return betweenMinutes > 5 && betweenMinutes <= 30;
    }

    public boolean isAbsent(LocalTime attendanceTime) {
        long betweenMinutes = Duration.between(dayOfWeek.getStandardTime(), attendanceTime).toMinutes();
        return betweenMinutes > 30;
    }


}
