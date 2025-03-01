package attendance.domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import attendance.exception.ExceptionMessage;

public enum LectureTime {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 00), LocalTime.of(18, 00)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 00), LocalTime.of(18, 00)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 00), LocalTime.of(18, 00)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 00), LocalTime.of(18, 00)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 00), LocalTime.of(18, 00)),
    SATURDAY(DayOfWeek.SATURDAY, null, null),
    SUNDAY(DayOfWeek.SUNDAY, null, null),
    ;

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    LectureTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static LectureTime from(LocalDate date) {
        return Arrays.stream(values())
            .filter(lectureTime -> lectureTime.dayOfWeek == date.getDayOfWeek())
            .findAny()
            .orElseThrow(() -> new IllegalStateException(ExceptionMessage.INVALID_LECTURE_STATE.getMessage(date)));
    }

    public int getLateTimeOf(LocalTime time) {
        Duration duration = Duration.between(startTime, time);
        return (int)duration.toMinutes();
    }
}
