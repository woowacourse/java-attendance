package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import util.DayOfWeekConverter;

public class Day {
    private static final int CHRISTMAS = 25;
    private final int day;

    public Day(int day, LocalDateTime today) {
        validateDay(day, today);
        this.day = day;
    }

    private void validateDay(int day, LocalDateTime today) {
        if(isHoliday(today)) {
            throw new IllegalArgumentException(String.format("%d월 %d일 %s은 등교일이 아닙니다.", today.getMonth().getValue(), day,
                    DayOfWeekConverter.convertDayOfWeek(day, today)));
        }
        if(isFuture(day, today)) {
            throw new IllegalArgumentException("미래 날짜는 수정할 수 없습니다.");
        }
    }


    public static boolean isFuture(int date, LocalDateTime today) {
        return today.getDayOfMonth() < date;
    }

    public static boolean isHoliday(int date, LocalDateTime today) {
        LocalDate targetDate = LocalDate.of(today.getYear(), today.getMonth(), date);
        if (date == CHRISTMAS) {
            return true;
        }
        return targetDate.getDayOfWeek().getValue() == 6 || targetDate.getDayOfWeek().getValue() == 7;
    }

    public static boolean isHoliday(LocalDateTime today) {
        if (today.getDayOfMonth() == CHRISTMAS) {
            return true;
        }
        return today.getDayOfWeek().getValue() == 6 || today.getDayOfWeek().getValue() == 7;
    }
}
