package domain;

import java.util.List;
import view.ErrorCode;

public class Calendar {
    private static final List<Integer> SATURDAYS = List.of(7, 14, 21, 28);
    private static final List<Integer> SUNDAYS = List.of(1, 8, 15, 22, 29);
    private static final List<Integer> HOLIDAYS = List.of(25);
    private static final List<Integer> MONDAYS = List.of(2, 9, 16, 23, 30);

    public static void validateIsWorkingDay(int date) {
        if (SATURDAYS.contains(date)) {
            throw new IllegalArgumentException(ErrorCode.SATURDAY_NOT_WORKING_DAY_FORMAT.format(date));
        }
        if (SUNDAYS.contains(date)) {
            throw new IllegalArgumentException(ErrorCode.SUNDAY_NOT_WORKING_DAY_FORMAT.format(date));
        }
        if (HOLIDAYS.contains(date)) {
            throw new IllegalArgumentException(ErrorCode.HOLIDAY_NOT_WORKING_DAY_FORMAT.format(date));
        }
    }

    public static boolean isMonday(int date) {
        return MONDAYS.contains(date);
    }

    public static boolean checkIsWorkingDay(int date) {
        return !SATURDAYS.contains(date) && !SUNDAYS.contains(date) && !HOLIDAYS.contains(date);
    }
}