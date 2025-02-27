package attendance.domain.checker;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class HolidayChecker {

    private Set<LocalDate> publicHolidays = new HashSet<>();

    public void addPublicHoliday(LocalDate publicHoliday) {
        publicHolidays.add(publicHoliday);
    }

    public void validateNotHoliday(LocalDate date) {
        if (checkHoliday(date)) {
            String exceptionMessage = String.format(ExceptionMessage.HOLIDAY_ATTENDANCE.getMessage(),
                    date.getMonth().getValue(), date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA));
            throw new AttendanceException(exceptionMessage);
        }
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

