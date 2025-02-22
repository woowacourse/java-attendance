package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public enum Current {
    TODAY(2024, 12, 13);

    public static final int CHRISTMAS = 25;

    private final int year;
    private final int month;
    private final int day;

    Current(final int year, final int month, final int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static boolean isDayOff(int day) {
        LocalDate targetDate = LocalDate.of(2024, 12, day);
        return targetDate.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue()
                || targetDate.getDayOfMonth() == CHRISTMAS;
    }

    public List<Integer> getAttendUntilDay() {
        return IntStream.range(1, day)
                .filter(day -> !isDayOff(day))
                .boxed()
                .toList();
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
