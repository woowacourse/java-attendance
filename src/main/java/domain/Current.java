package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public enum Current {
    TODAY(13);

    public static final int YEAR = 2024;
    public static final int MONTH = 12;

    private final int day;

    Current(final int day) {
        this.day = day;
    }

    public static boolean isDayOff(int day) {
        LocalDate targetDate = LocalDate.of(YEAR, MONTH, day);
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
        return LocalDate.of(YEAR, MONTH, day);
    }

    public int getDay() {
        return day;
    }

    public int getLengthOfMonth() {
        return getLocalDate().lengthOfMonth();
    }
}
