package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum StandardTime {

    MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 5), LocalTime.of(13, 30)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 5), LocalTime.of(10, 30)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 5), LocalTime.of(10, 30)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 5), LocalTime.of(10, 30)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 5), LocalTime.of(10, 30));

    private final DayOfWeek dayOfWeek;
    private final LocalTime lateTime;
    private final LocalTime absentTime;

    StandardTime(DayOfWeek dayOfWeek, LocalTime lateTime, LocalTime absentTime) {
        this.dayOfWeek = dayOfWeek;
        this.lateTime = lateTime;
        this.absentTime = absentTime;
    }

    public static StandardTime findByDayOfWeek(DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
                .filter(standardTime -> standardTime.dayOfWeek == dayOfWeek)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 요일의 기준 시간 정보가 없습니다."));
    }

    public LocalTime getAbsentTime() {
        return absentTime;
    }

    public LocalTime getLateTime() {
        return lateTime;
    }
}
