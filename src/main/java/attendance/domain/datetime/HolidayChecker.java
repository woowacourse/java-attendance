package attendance.domain.datetime;

import attendance.exception.ExceptionMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HolidayChecker {

    private final List<LocalDate> publicHolidays = new ArrayList<>();

    public void addHoliday(LocalDate holiday) {
        if (publicHolidays.contains(holiday)) {
            String content = ExceptionMessage.ALREADY_EXIST_HOLIDAY.getContent();
            throw new IllegalArgumentException(content);
        }
        publicHolidays.add(holiday);
    }

    public boolean isHoliday(LocalDate date) {
        boolean isSaturday = date.getDayOfWeek() == DayOfWeek.SATURDAY;
        boolean isSunday = date.getDayOfWeek() == DayOfWeek.SUNDAY;
        boolean isPublicHoliday = publicHolidays.contains(date);
        return isSaturday || isSunday || isPublicHoliday;
    }

    public void validateHoliday(LocalDate date) {
        boolean isHoliday = isHoliday(date);
        if (isHoliday) {
            String message = String.format(ExceptionMessage.HOLIDAY.getContent(),
                    date.getMonth().getValue(), date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
            throw new IllegalArgumentException(message);
        }
    }

    public int calculateNotHolidayCount(LocalDate startDate, LocalDate endDate) {
        return (int) startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> !isHoliday(date))
                .count();
    }
}
