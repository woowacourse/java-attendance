package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class AttendanceDate {

    private final LocalDate date;

    public AttendanceDate(LocalDate date) {
        validateWeekendAndHoliday(date);
        this.date = date;
    }

    private static void validateWeekendAndHoliday(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || isHoliday(date)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.", date.getMonth().getValue(),
                            date.getDayOfMonth(), date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }

    private static boolean isHoliday(LocalDate date) {
        return MonthDay.from(date).equals(MonthDay.of(12, 25));
    }

    public boolean isSameDay(int day) {
        return date.getDayOfMonth() == day;
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceDate that = (AttendanceDate) object;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }

    public int getDayOfMonth() {
        return date.getDayOfMonth();
    }
}
