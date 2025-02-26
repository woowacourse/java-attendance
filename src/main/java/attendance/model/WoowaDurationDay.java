package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

public enum WoowaDurationDay {
    MONDAY(DayOfWeek.MONDAY, true),
    TUESDAY(DayOfWeek.TUESDAY, true),
    WEDNESDAY(DayOfWeek.WEDNESDAY, true),
    THURSDAY(DayOfWeek.THURSDAY, true),
    FRIDAY(DayOfWeek.FRIDAY, true),
    SATURDAY(DayOfWeek.SATURDAY, false),
    SUNDAY(DayOfWeek.SUNDAY, false),
    ;
    private final DayOfWeek dayOfWeek;
    private final boolean operation;
    private static final Set<LocalDate> holidays = Set.of(LocalDate.of(2024, 12, 25));

    WoowaDurationDay(DayOfWeek dayOfWeek, boolean operation) {
        this.dayOfWeek = dayOfWeek;
        this.operation = operation;
    }

    public static boolean isDurationDay(LocalDate date) {
        if (holidays.contains(date)) {
            return false;
        }
        return Arrays.stream(WoowaDurationDay.values())
                .filter(woowaDuration -> woowaDuration.dayOfWeek == date.getDayOfWeek())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 날짜입니다.")).operation;
    }
}
