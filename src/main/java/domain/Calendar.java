package domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public enum Calendar {
    MONDAYS(List.of(2, 9, 16, 23, 30)),
    SATURDAYS(List.of(7, 13, 21, 28)),
    SUNDAYS(List.of(1, 8, 15, 22, 29)),
    HOLIDAYS(List.of(25)),
    WORKING_DAYS(null);

    private final List<Integer> days;

    Calendar(List<Integer> days) {
        this.days = days;
    }

    public Calendar findByDate(LocalDate date) {
        int day = date.getDayOfMonth();

        return Arrays.stream(Calendar.values())
                .filter(calendar -> calendar.containsDay(day))
                .findAny()
                .orElse(WORKING_DAYS);
    }

    private boolean containsDay(int value) {
        return days.contains(value);
    }
}
