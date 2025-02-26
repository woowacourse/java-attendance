package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public static LocalDateTime calculateBoundaryTime(final LocalDate date) {
        return Arrays.stream(ClassTime.values())
                .filter(classTime -> Objects.equals(classTime.dayOfWeek, date.getDayOfWeek()) && !Holiday.isHoliday(date))
                .findAny()
                .map(classTime -> LocalDateTime.of(date, classTime.startTime))
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 주말 또는 공휴일은 등교일이 아닙니다."));
    }

}
