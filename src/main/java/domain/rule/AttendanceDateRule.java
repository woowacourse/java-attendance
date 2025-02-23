package domain.rule;

import util.TimeMachine;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.stream.Stream;

public enum AttendanceDateRule {

    CHRISTMAS("크리스마스", 12, 25),
    ;

    private final String description;
    private final int month;
    private final int dayOfMonth;

    AttendanceDateRule(String description, int month, int dayOfMonth) {
        this.description = description;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(TimeMachine.FIXED_YEAR, this.month, this.dayOfMonth);
    }

    public static boolean canAttendDay(LocalDate date) {
        return !isHoliday(date) && !isWeekend(date.getDayOfWeek());
    }

    public static boolean isHoliday(LocalDate date) {
        return Stream.of(AttendanceDateRule.values())
                .anyMatch(holiday ->
                        MonthDay.from(holiday.toLocalDate()).equals(MonthDay.from(date)));
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static boolean isSpecialDay(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.MONDAY);
    }
}
