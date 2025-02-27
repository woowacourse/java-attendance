package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Day {
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;
    private static final List<Integer> HOLIDAYS = List.of(25);

    public static boolean isFuture(int date, LocalDateTime today) {
        return today.getDayOfMonth() < date;
    }

    public static boolean isHoliday(int date, LocalDateTime today) {
        LocalDate targetDate = LocalDate.of(today.getYear(), today.getMonth(), date);
        if (HOLIDAYS.contains(date)) {
            return true;
        }
        return targetDate.getDayOfWeek().getValue() == SATURDAY || targetDate.getDayOfWeek().getValue() == SUNDAY;
    }

    public static boolean isHoliday(LocalDateTime today) {
        if (HOLIDAYS.contains(today.getDayOfMonth())) {
            return true;
        }
        return today.getDayOfWeek().getValue() == SATURDAY || today.getDayOfWeek().getValue() == SUNDAY;
    }

    public static void validateDay(int day, LocalDateTime today) {
        if (isHoliday(today)) {
            throw new IllegalArgumentException(String.format("%d월 %d일 %s은 등교일이 아닙니다.", today.getMonth().getValue(), day,
                    DayOfWeekConverter.convertDayOfWeek(day, today)));
        }
        if (isFuture(day, today)) {
            throw new IllegalArgumentException("미래 날짜는 수정할 수 없습니다.");
        }
    }

    public static LocalDate toLocalDate(int day, LocalDateTime today) {
        return LocalDate.of(today.getYear(), today.getMonth(), day);
    }
}
