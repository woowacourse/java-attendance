package attendance.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public enum WoowaDurationTime {
    월요일(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)),
    화요일(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    수요일(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    목요일(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    금요일(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(18, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE은 등교일이 아닙니다.");

    WoowaDurationTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public static long calculateDuration(LocalDateTime localDateTime) {
        return Duration.between(getStartTime(localDateTime), localDateTime.toLocalTime()).toMinutes();
    }

    private static LocalTime getStartTime(LocalDateTime localDateTime) {
        return Arrays.stream(WoowaDurationTime.values())
                .filter(woowaDurationTime -> woowaDurationTime.dayOfWeek == localDateTime.getDayOfWeek())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 운영시간입니다.")).startTime;
    }
}
