package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public enum Day {
    MONDAY("월요일", DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUESDAY("화요일", DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WEDNESDAY("수요일", DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THURSDAY("목요일", DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRIDAY("금요일", DayOfWeek.FRIDAY, LocalTime.of(10, 0)),
    SATURDAY("토요일", DayOfWeek.SATURDAY, LocalTime.of(0, 0)),
    SUNDAY("일요일", DayOfWeek.SUNDAY, LocalTime.of(0, 0));

    private static final List<LocalDate> HOLIDAYS = List.of(LocalDate.of(2024, 12, 25));

    private final String name;
    private final DayOfWeek dayOfWeek;
    private final LocalTime start;

    Day(String name, DayOfWeek dayOfWeek, LocalTime start) {
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.start = start;
    }

    public static boolean checkHoliday(LocalDate date) {
        if (HOLIDAYS.contains(date)) {
            return true;
        }
        return Arrays.stream(Day.values())
                .filter(day -> day.dayOfWeek.equals(date.getDayOfWeek()))
                .anyMatch(day -> day.dayOfWeek.equals(DayOfWeek.SATURDAY) || day.dayOfWeek.equals(DayOfWeek.SUNDAY));
    }

    public static Day getDay(LocalDate date) {
        return Arrays.stream(Day.values())
                .filter(day -> day.dayOfWeek.equals(date.getDayOfWeek()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] getDay 메서드 오류"));
    }

    public LocalTime getStart() {
        return start;
    }

    public String getName() {
        return name;
    }
}
