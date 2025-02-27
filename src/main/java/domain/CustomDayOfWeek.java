package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public enum CustomDayOfWeek {
    MONDAY(DayOfWeek.MONDAY, "월요일", LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, "화요일", LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, "수요일", LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, "목요일", LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, "금요일", LocalTime.of(10, 0));

    private final DayOfWeek dayOfWeek;
    private final String name;
    private final LocalTime criteriaTime;

    CustomDayOfWeek(java.time.DayOfWeek dayOfWeek, String name, LocalTime criteriaTime) {
        this.dayOfWeek = dayOfWeek;
        this.name = name;
        this.criteriaTime = criteriaTime;
    }

    public static LocalTime getCriteriaTime(LocalDate date) {
        return Arrays.stream(CustomDayOfWeek.values())
                .filter(dayOfWeek -> dayOfWeek.dayOfWeek.equals(date.getDayOfWeek()))
                .findFirst()
                .map(dayOfWeek -> dayOfWeek.criteriaTime)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Boolean isWeekDay(LocalDate date) {
        return Arrays.stream(CustomDayOfWeek.values())
                .anyMatch(dayOfWeek -> dayOfWeek.dayOfWeek.equals(date.getDayOfWeek()));
    }

}
