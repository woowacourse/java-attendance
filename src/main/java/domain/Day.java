package domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Day {

    private LocalDate date;
    private Boolean isHoliday = false;
    private DayOfWeek dayOfWeek;

    public Day(LocalDate date) {
        this.date = date;
        this.dayOfWeek = DayOfWeek.getInstance(date);
        checkHoliday();
    }

    private void checkHoliday() {
        if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            isHoliday = true;
        }
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
