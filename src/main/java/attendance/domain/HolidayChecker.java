package attendance.domain;

import attendance.exception.ExceptionMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
}
