package attendance.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public enum WoowaDurationTime {
    월요일(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    화요일(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    수요일(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    목요일(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    금요일(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;

    WoowaDurationTime(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static long calculateDuration(LocalDateTime localDateTime) {
        return Duration.between(getStartTime(localDateTime.getDayOfWeek()), localDateTime.toLocalTime()).toMinutes();
    }

    private static LocalTime getStartTime(DayOfWeek dayOfWeek) {
        return Arrays.stream(WoowaDurationTime.values())
                .filter(woowaDurationTime -> woowaDurationTime.dayOfWeek == dayOfWeek)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 운영시간입니다.")).startTime;
    }
}
