package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class EducationDay {
    private final Set<LocalDate> holidays;

    public EducationDay(Set<LocalDate> holidays) {
        this.holidays = holidays;
    }

    public boolean isEducationDay(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }
        if (holidays.contains(date)) {
            return false;
        }
        return true;
    }
}
