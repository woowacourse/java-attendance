package attendance.domain;

import static attendance.error.ErrorMessage.NO_EDUCATION_DAY;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum EducationTime {
    MONDAY("월요일", DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY("화요일", DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY("수요일", DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY("목요일", DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY("금요일", DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(18, 0));

    private final String name;
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    EducationTime(String name, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.name = name;
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
