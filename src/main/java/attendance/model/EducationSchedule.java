package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public enum EducationSchedule {

    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    ;

    private final DayOfWeek day;
    private final LocalTime startTime;
    private final LocalTime endTime;

    EducationSchedule(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static LocalTime findStartTimeByDay(DayOfWeek day) {
        return Arrays.stream(values())
                .filter(educationSchedule -> educationSchedule.day == day)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 요일의 교육 일정이 없습니다. 입력: %s".formatted(day.getDisplayName(TextStyle.FULL, Locale.KOREAN))))
                .startTime;
    }
}
