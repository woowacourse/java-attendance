package domain.policy.attend.date;

import java.time.DayOfWeek;
import java.util.stream.Stream;

public enum SpecialDayOfWeekRule {

    DEEP_SLEEP(DayOfWeek.MONDAY, true, "우아한 숙면을 취하리"),
    I_WANNABE_SPECIAL_DAY(DayOfWeek.FRIDAY, false, "금요일도 늦게 등교한다면 어떨까");

    private final DayOfWeek dayOfWeek;
    private final boolean isActive;
    private final String description;

    SpecialDayOfWeekRule(DayOfWeek dayOfWeek, boolean isActive, String description) {
        this.dayOfWeek = dayOfWeek;
        this.isActive = isActive;
        this.description = description;
    }

    public static boolean isSpecialDayOfWeek(DayOfWeek dayOfWeek) {
        return Stream.of(SpecialDayOfWeekRule.values())
                .filter(specialDay -> specialDay.isActive)
                .anyMatch(specialDayOfWeek -> specialDayOfWeek.dayOfWeek.equals(dayOfWeek));
    }

    public String getDescription() {
        return description;
    }
}
