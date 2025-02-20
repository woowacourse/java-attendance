package domain;

import java.util.List;

public class Calendar {
    private static final List<Integer> SATURDAYS = List.of(7, 14, 21, 28);
    private static final List<Integer> SUNDAYS = List.of(1, 8, 15, 22, 29);
    private static final List<Integer> HOLIDAYS = List.of(25);
    private static final List<Integer> MONDAYS = List.of(2, 9, 16, 23, 30);

    public static void validateIsWorkingDay(int date) {
        if (SATURDAYS.contains(date)) {
            throw new IllegalArgumentException(String.format("[ERROR] 12월 %02d일 토요일은 등교일이 아닙니다.", date));
        }
        if (SUNDAYS.contains(date)) {
            throw new IllegalArgumentException(String.format("[ERROR] 12월 %02d일 일요일은 등교일이 아닙니다.", date));
        }
        if (HOLIDAYS.contains(date)) {
            throw new IllegalArgumentException(String.format("[ERROR] 12월 %02d일 공휴일은 등교일이 아닙니다.", date));
        }
    }

    public static boolean isMonday(int date) {
        return MONDAYS.contains(date);
    }

    public static boolean checkIsWorkingDay(int date) {
        if (SATURDAYS.contains(date) || SUNDAYS.contains(date) || HOLIDAYS.contains(date)) {
            return false;
        }
        return true;
    }
}