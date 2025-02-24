package attendance.constant;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum StartTimeRule {

    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;

    StartTimeRule(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static LocalTime getStartTime(DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
            .filter(timeRule -> timeRule.dayOfWeek == dayOfWeek)
            .map(timeRule -> timeRule.startTime)
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }
}
