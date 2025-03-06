package domain.policy.attend.date;

import java.time.DayOfWeek;
import java.util.stream.Stream;

public enum WeekendRule {

    FRIDAY(DayOfWeek.FRIDAY, false, "금요일이 주말이 되길"),
    SATURDAY(DayOfWeek.SATURDAY, true, "놀토가 돌아오지 않길"),
    SUNDAY(DayOfWeek.SUNDAY, true, "일요일");

    private final DayOfWeek dayOfWeek;
    private final boolean isActive;
    private final String description;

    WeekendRule(DayOfWeek dayOfWeek, boolean isActive, String description) {
        this.dayOfWeek = dayOfWeek;
        this.isActive = isActive;
        this.description = description;
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return Stream.of(WeekendRule.values())
                .filter(weekendRule -> weekendRule.isActive)
                .anyMatch(weekendRule -> dayOfWeek.equals(weekendRule.dayOfWeek));
    }

    public String getDescription() {
        return description;
    }
}
