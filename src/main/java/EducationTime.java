import exception.InvalidDateException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum EducationTime {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    ;

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    EducationTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static LocalTime startOf(DayOfWeek dayOfWeek) {
        EducationTime educationTime = Arrays.stream(values())
                .filter(value -> value.dayOfWeek == dayOfWeek)
                .findFirst()
                .orElseThrow(InvalidDateException::new);
        return educationTime.startTime;
    }

    public static LocalTime endOf(DayOfWeek dayOfWeek) {
        EducationTime educationTime = Arrays.stream(values())
                .filter(value -> value.dayOfWeek == dayOfWeek)
                .findFirst()
                .orElseThrow(InvalidDateException::new);
        return educationTime.endTime;
    }

    public static boolean isOperatingOn(DayOfWeek day) {
        return Arrays.stream(values()).anyMatch(value -> value.dayOfWeek == day);
    }
}
