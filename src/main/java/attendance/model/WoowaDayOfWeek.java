package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public enum WoowaDayOfWeek {
    월요일(DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(18, 0)),
    화요일(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    수요일(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    목요일(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    금요일(DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)),
    토요일(DayOfWeek.SATURDAY, null, null),
    일요일(DayOfWeek.SUNDAY, null, null),
    ;

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    WoowaDayOfWeek(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WoowaDayOfWeek from(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        return Arrays.stream(WoowaDayOfWeek.values())
                .filter(woowaDayOfWeek -> woowaDayOfWeek.dayOfWeek.equals(dayOfWeek))
                .findFirst()
                .get();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
