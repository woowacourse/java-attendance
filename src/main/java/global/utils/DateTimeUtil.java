package global.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateTimeUtil {
    public static final LocalDateTime FIXED_RUNNING_DATETIME = LocalDateTime.of(2024, 12, 14, 10, 0, 0);
    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2024, 12, 25);
    public static final LocalTime START_RUNNING_TIME = LocalTime.of(8, 0);
    public static final LocalTime END_RUNNING_TIME = LocalTime.of(23, 0);

    public static final List<LocalDate> NATIONAL_HOLIDAYS = List.of(CHRISTMAS_DATE);

    public static LocalDate getFixedRunningDate() {
        return FIXED_RUNNING_DATETIME.toLocalDate();
    }

    public static LocalTime getFixedRunningTime() {
        return FIXED_RUNNING_DATETIME.toLocalTime();
    }

    public static boolean isWeekday(LocalDate date) {
        return !(isWeekend(date) || isHoliday(date));
    }

    public static boolean isOutOfRunningTime(LocalTime time) {
        return time.isAfter(END_RUNNING_TIME) || time.isBefore(START_RUNNING_TIME);
    }

    public static boolean isDateInAvailableAttendance(LocalDate date) {
        LocalDate firstDayOfMonth = FIXED_RUNNING_DATETIME.withDayOfMonth(1).toLocalDate();
        return !(date.isAfter(FIXED_RUNNING_DATETIME.toLocalDate()) || date.isBefore(firstDayOfMonth));
    }

    public static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time);
        }  catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바르지 않은 형식입니다.");
        }
    }

    public static LocalDate parseDateOfThisMonth(String day) {
        try {
            return FIXED_RUNNING_DATETIME.withDayOfMonth(Integer.parseInt(day)).toLocalDate();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바르지 않은 형식입니다.");
        }

    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private static boolean isHoliday(LocalDate date) {
        return NATIONAL_HOLIDAYS.contains(date);
    }
}
