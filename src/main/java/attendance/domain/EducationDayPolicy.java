package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class EducationDayPolicy {
    private final Set<LocalDate> holidays;

    public EducationDayPolicy(Set<LocalDate> holidays) {
        this.holidays = holidays;
    }

    public boolean isEducationDay(LocalDate date) {
        return !isWeekend(date) && !holidays.contains(date);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
