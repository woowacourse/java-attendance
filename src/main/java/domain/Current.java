package domain;

import java.time.LocalDate;

public enum Current {
    TODAY(2024, 12, 13);

    private final LocalDate date;

    Current(final int year, final int month, final int day) {
        this.date = LocalDate.of(year, month, day);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getYear() {
        return this.date.getYear();
    }

    public int getMonth() {
        return this.date.getMonth().getValue();
    }

    public int getDay() {
        return this.date.getDayOfMonth();
    }
}
