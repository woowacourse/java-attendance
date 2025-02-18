import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public enum Day {
    MONDAY("월요일", DayOfWeek.MONDAY, LocalTime.of(13, 0), false),
    TUESDAY("화요일", DayOfWeek.TUESDAY, LocalTime.of(10, 0), false),
    WEDNESDAY("수요일", DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), false),
    THURSDAY("목요일", DayOfWeek.THURSDAY, LocalTime.of(10, 0), false),
    FRIDAY("금요일", DayOfWeek.FRIDAY, LocalTime.of(10, 0), false),
    SATURDAY("토요일", DayOfWeek.SATURDAY, LocalTime.of(0, 0), true),
    SUNDAY("일요일", DayOfWeek.SUNDAY, LocalTime.of(0, 0), true);

    private static final List<LocalDate> HOLIDAYS = List.of(LocalDate.of(2024, 12, 25));

    private final String name;
    private final DayOfWeek dayOfWeek;
    private final LocalTime start;
    private final boolean isHoliday;

    Day(String name, DayOfWeek dayOfWeek, LocalTime start, boolean isHoliday) {
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.isHoliday = isHoliday;
    }

    public static boolean checkHoliday(LocalDate date) {
        if (HOLIDAYS.contains(date)) {
            return true;
        }
        Day targetDay = Arrays.stream(Day.values())
                .filter(day -> day.dayOfWeek.equals(date.getDayOfWeek()))
                .findFirst()
                .orElse(Day.MONDAY);
        return targetDay.isHoliday;
    }

    public static Day getDay(LocalDate date) {
        return Arrays.stream(Day.values())
                .filter(day -> day.dayOfWeek.equals(date.getDayOfWeek()))
                .findFirst()
                .orElse(Day.MONDAY);
    }

    public LocalTime getStart() {
        return start;
    }

    public String getName() {
        return name;
    }
}
