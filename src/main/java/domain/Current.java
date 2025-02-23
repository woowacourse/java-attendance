package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public enum Current {
    TODAY(12, 13);

    public static final int YEAR = 2024;

    private final int year;
    private final int month;
    private final int day;

    Current(final int month, final int day) {
        this.year = YEAR;
        this.month = month;
        this.day = day;
    }

    public static boolean isDayOff(int day) {
        LocalDate targetDate = LocalDate.of(YEAR, 12, day);
        return targetDate.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue()
                || Holiday.isHoliday(targetDate);
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
}
