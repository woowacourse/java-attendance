package constant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public enum LectureTime {
    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    ;
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    LectureTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static boolean isLectureDate(LocalDate date) {
        return Arrays.stream(values())
                .map(lectureTime -> lectureTime.dayOfWeek)
                .anyMatch(dayOfWeek1 -> dayOfWeek1 == date.getDayOfWeek());
    }
}
