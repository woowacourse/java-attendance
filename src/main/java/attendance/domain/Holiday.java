package attendance.domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    NEW_YEAR(1, 1),
    INDEPENDENCE_MOVEMENT_DAY(3, 1),
    CHILDRENS_DAY(5, 5),
    MEMORIAL_DAY(6, 6),
    LIBERATION_DAY(8, 15),
    NATIONAL_FOUNDATION_DAY(10, 3),
    HANGUL_DAY(10, 9),
    CHRISTMAS(12, 25);

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(values())
                .map(holiday -> LocalDate.of(date.getYear(), holiday.month, holiday.day))
                .anyMatch(holidayDate -> holidayDate.equals(date));
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
