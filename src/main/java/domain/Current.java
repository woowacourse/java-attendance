package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public enum Current {
    TODAY;

    private final LocalDate date;

    Current() {
        this.date = LocalDate.of(2024, 12, 13);
    }

    public static boolean isDayOff(int day) {
        LocalDate targetDate = LocalDate.of(TODAY.getYear(), TODAY.getMonth(), day);
        return targetDate.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue()
                || Holiday.isHoliday(targetDate);
    }

    public List<Integer> getAttendUntilDay() {
        return IntStream.range(1, getDay())
                .filter(day -> !isDayOff(day))
                .boxed()
                .toList();
    }

    public int getLengthOfMonth() {
        return getDate().lengthOfMonth();
    }

    public LocalDate getDate() {
        return date;
    }

    public int getYear() {
        return this.date.getYear();
    }

    public int getMonth() {
        return this.date.getMonthValue();
    }

    public int getDay() {
        return this.date.getDayOfMonth();
    }
}
