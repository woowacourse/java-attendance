package policy.date;

import policy.date.rule.HolidayRule;
import policy.date.rule.SpecialDayOfWeekRule;
import policy.date.rule.WeekendRule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;

public class AttendanceDatePolicy {

    public boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return WeekendRule.isWeekend(dayOfWeek);
    }

    public boolean isHoliday(LocalDate date) {
        MonthDay monthDay = MonthDay.of(date.getMonth(), date.getDayOfMonth());
        return HolidayRule.isHoliday(monthDay);
    }

    public boolean isSpecialDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return SpecialDayOfWeekRule.isSpecialDayOfWeek(dayOfWeek);
    }
}
