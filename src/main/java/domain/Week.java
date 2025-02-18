package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public enum Week {
    MONDAY(LocalTime.of(13, 0)),
    TUESDAY(LocalTime.of(10, 0)),
    WEDNESDAY(LocalTime.of(10, 0)),
    THURSDAY(LocalTime.of(10, 0)),
    FRIDAY(LocalTime.of(10, 0));


    private final LocalTime attendanceTime;

    Week(final LocalTime localTime) {
        this.attendanceTime = localTime;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public static Week findByAttendanceTime(final LocalDateTime localDateTime) {
        DayOfWeek day = localDateTime.getDayOfWeek();

        return Arrays.stream(Week.values())
                .filter(week -> week.name().equals(day.name()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
