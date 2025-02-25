package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class HolidayChecker {

    private Set<LocalDate> publicHolidays = new HashSet<>();

    public void addPublicHoliday(LocalDate publicHoliday) {
        publicHolidays.add(publicHoliday);
    }


    public boolean checkHoliday(LocalDate date) {
        return isWeekend(date) || isPublicHoliday(date);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isPublicHoliday(LocalDate date) {
        return publicHolidays.contains(date);
    }
}

