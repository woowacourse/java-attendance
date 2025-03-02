package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public enum ClassSchedule {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

    private static final List<LocalDate> HOLIDAYS = List.of(LocalDate.of(2024, 12, 25));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;

    ClassSchedule(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static LocalTime getStartTime(DayOfWeek day) {
        return Arrays.stream(ClassSchedule.values())
                .filter(schedule -> schedule.dayOfWeek == day)
                .findAny()
                .orElseThrow(IllegalStateException::new)
                .startTime;
    }

    public static boolean isDayOff(LocalDate date) {
        if (HOLIDAYS.contains(date)) {
            return true;
        }
        DayOfWeek targetDay = date.getDayOfWeek();
        return Arrays.stream(ClassSchedule.values())
                .noneMatch(schedule -> schedule.dayOfWeek == targetDay);
    }
}
