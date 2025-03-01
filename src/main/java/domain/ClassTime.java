package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

public enum ClassTime {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;

    ClassTime(final DayOfWeek dayOfWeek, final LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static LocalTime findByDayOfWeek(DayOfWeek dayOfWeek) {
        return Arrays.stream(ClassTime.values())
                .filter(classTime -> Objects.equals(classTime.dayOfWeek, dayOfWeek))
                .map(classTime -> classTime.startTime)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
