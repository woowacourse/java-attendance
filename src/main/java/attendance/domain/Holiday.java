package attendance.domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {

    새해_첫날(1, 1),
    삼일절(3, 1),
    어린이날(5, 5),
    현충일(6, 6),
    광복절(8, 15),
    개천절(10, 3),
    한글날(10, 9),
    크리스마스(12, 25);

    private final int month;
    private final int day;

    Holiday(final int month, final int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isExists(final LocalDate localDate) {
        return Arrays.stream(values())
                .anyMatch(holiday ->
                        holiday.month == localDate.getMonthValue() && holiday.day == localDate.getDayOfMonth());
    }
}
