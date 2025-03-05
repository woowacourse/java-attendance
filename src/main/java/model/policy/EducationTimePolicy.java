package model.policy;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum EducationTimePolicy {
    MON(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUE(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WED(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THU(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRI(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(18, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime finishTime;

    EducationTimePolicy(final DayOfWeek dayOfWeek, final LocalTime startTime, final LocalTime finishTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public static LocalTime getStartTime(final DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
                .filter(value -> dayOfWeek.equals(value.dayOfWeek))
                .findAny()
                .orElseThrow(IllegalStateException::new)
                .startTime;
    }
}
