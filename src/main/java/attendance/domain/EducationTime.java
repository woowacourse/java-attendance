package attendance.domain;

import static attendance.error.ErrorMessage.NO_EDUCATION_DAY;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum EducationTime {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(18, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    EducationTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static EducationTime from(DayOfWeek dayOfWeek) {
        return Arrays.stream(EducationTime.values())
                .filter(educationTime -> educationTime.dayOfWeek == dayOfWeek)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NO_EDUCATION_DAY));
    }

    public LocalTime getStartTime() {
        return startTime;
    }

}
