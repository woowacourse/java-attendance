package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HolidayChecker {

    private final List<LocalDate> publicHolidays = new ArrayList<>();

    public void addHoliday(LocalDate holiday) {
        if (publicHolidays.contains(holiday)) {
            throw new IllegalArgumentException("[ERROR] 이미 추가된 휴일입니다.");
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
