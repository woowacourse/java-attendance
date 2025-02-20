package attendance.domain.constant;

import java.util.Arrays;

public enum DayOfWeek {

    MONDAY(1, "월요일"),
    TUESDAY(2, "화요일"),
    WEDNESDAY(3, "수요일"),
    THURSDAY(4, "목요일"),
    FRIDAY(5, "금요일"),
    SATURDAY(6, "토요일"),
    SUNDAY(7, "일요일"),
    ;

    private final int day;
    private final String dayOfWeek;

    DayOfWeek(final int day, final String dayOfWeek) {
        this.day = day;
        this.dayOfWeek = dayOfWeek;
    }

    public static DayOfWeek from(final int date) {
        return Arrays.stream(DayOfWeek.values())
                .filter(day -> day.day == date)
                .findFirst()
                .orElseThrow();
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
