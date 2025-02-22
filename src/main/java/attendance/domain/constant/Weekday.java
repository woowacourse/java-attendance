package attendance.domain.constant;

import java.time.DayOfWeek;
import java.util.Arrays;

public enum Weekday {

    MONDAY(DayOfWeek.MONDAY, "월요일"),
    TUESDAY(DayOfWeek.TUESDAY, "화요일"),
    WEDNESDAY(DayOfWeek.WEDNESDAY, "수요일"),
    THURSDAY(DayOfWeek.THURSDAY, "목요일"),
    FRIDAY(DayOfWeek.FRIDAY, "금요일"),
    SATURDAY(DayOfWeek.SATURDAY, "토요일"),
    SUNDAY(DayOfWeek.SUNDAY, "일요일"),
    ;

    private final DayOfWeek day;
    private final String dayOfWeek;

    Weekday(final DayOfWeek day, final String dayOfWeek) {
        this.day = day;
        this.dayOfWeek = dayOfWeek;
    }

    public static Weekday from(final DayOfWeek date) {
        return Arrays.stream(Weekday.values())
                .filter(day -> day.day == date)
                .findFirst()
                .orElseThrow();
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

}
