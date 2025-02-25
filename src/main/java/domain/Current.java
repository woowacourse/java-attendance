package domain;

import java.time.LocalDate;

public enum Current {
    TODAY(2024, 12, 13);

    private final int year;
    private final int month;
    private final int day;

    Current(final int year, final int month, final int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public LocalDate getLocalDate() {
        return LocalDate.of(year, month, day);
    }

    public int getDay() {
        return day;
    }

    public int getYesterday() {
        return day - 1;
    }
}
