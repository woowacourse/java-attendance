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

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private static final List<LocalDate> holidays = List.of(LocalDate.of(2024, 12, 25));

    ClassSchedule(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static LocalTime getStartTimeOf(DayOfWeek day) {
        return Arrays.stream(ClassSchedule.values())
                .filter(schedule -> schedule.dayOfWeek == day)
                .findAny()
                .orElseThrow(IllegalStateException::new)
                .getStartTime();
    }

    public static boolean isDayOff(LocalDate date) {
        if (holidays.contains(date)) {
            return true;
        }
        return Arrays.stream(ClassSchedule.values())
                .noneMatch(schedule -> schedule.dayOfWeek == date.getDayOfWeek());
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
