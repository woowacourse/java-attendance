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
    FRIDAY(DayOfWeek.FRIDAY, "금요일", LocalTime.of(10, 0)),
    SATURDAY(DayOfWeek.SATURDAY, "토요일", null),
    SUNDAY(DayOfWeek.SUNDAY, "일요일", null);

    private final DayOfWeek dayOfWeek;
    private final String name;
    private final LocalTime criteriaTime;

    CustomDayOfWeek(java.time.DayOfWeek dayOfWeek, String name, LocalTime criteriaTime) {
        this.dayOfWeek = dayOfWeek;
        this.name = name;
        this.criteriaTime = criteriaTime;
    }

    public static CustomDayOfWeek getInstance(LocalDate date) {
        return Arrays.stream(values())
                .filter(customDayOfWeek -> customDayOfWeek.dayOfWeek.equals(date.getDayOfWeek()))
                .findFirst()
                .orElse(null);
    }

    public static LocalTime getCriteriaTime(LocalDate date) {
        return Arrays.stream(CustomDayOfWeek.values())
                .filter(dayOfWeek -> dayOfWeek.dayOfWeek.equals(date.getDayOfWeek()))
                .findFirst()
                .map(dayOfWeek -> {
                    isWeekend(dayOfWeek);
                    return dayOfWeek.criteriaTime;
                })
                .orElse(null);
    }

    private static void isWeekend(CustomDayOfWeek dayOfWeek) {
        if (dayOfWeek.criteriaTime == null) {
            throw new IllegalArgumentException("[ERROR] 등교일이 아닙니다.");
        }
    }

    public static Boolean isWeekDay(LocalDate date) {
        return Arrays.stream(CustomDayOfWeek.values())
                .filter(customDayOfWeek -> customDayOfWeek.criteriaTime != null)
                .anyMatch(dayOfWeek -> dayOfWeek.dayOfWeek.equals(date.getDayOfWeek()));
    }

    public String getName() {
        return name;
    }

}
