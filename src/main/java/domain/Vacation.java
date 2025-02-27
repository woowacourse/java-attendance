package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Vacation {
    LEVEL1_VACATION(LocalDate.of(2025, 4, 7), LocalDate.of(2025, 4, 14)),
    LEVEL2_VACATION(LocalDate.of(2025, 6, 16), LocalDate.of(2025, 6, 30)),
    LEVEL3_VACATION(LocalDate.of(2025, 8, 25), LocalDate.of(2025, 9, 1));

    private final LocalDate start;
    private final LocalDate end;

    Vacation(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public static boolean isVacation(LocalDate date) {
        return Arrays.stream(Vacation.values())
                .anyMatch(vacation -> vacation.contains(date));
    }

    private boolean contains(LocalDate date) {
        return (date.isAfter(this.start) && date.isBefore(this.end))
                || date.isEqual(this.start) || date.isEqual(this.end);
    }
}
