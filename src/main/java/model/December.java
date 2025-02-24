package model;

import common.Common;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public class December {
    private static final int YEAR = 2024;
    private static final int MONTH = 12;
    private static final List<Integer> dates = IntStream.range(1, 32).boxed().toList();

    public static void validateHoliday(LocalDate date) {
        if (isHolidayAt(date)) {
            throw new IllegalArgumentException(
                    String.format("%s은 등교일이 아닙니다.", date.format(Common.monthDateDayFormatter)));
        }
    }

    private static boolean isHolidayAt(LocalDate date) {
        if (date.getYear() != YEAR || date.getMonthValue() != MONTH) {
            throw new RuntimeException("2024년 12월에 한정된 서비스입니다.");
        }
        return date.getDayOfMonth() == 25
                || date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
