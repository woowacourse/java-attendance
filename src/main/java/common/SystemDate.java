package common;

import java.time.LocalDate;

public enum SystemDate {

    NOW(LocalDate.of(2024, 12, 24)),
    START_DATE(NOW.date.withDayOfMonth(1));

    private final LocalDate date;

    SystemDate(final LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
