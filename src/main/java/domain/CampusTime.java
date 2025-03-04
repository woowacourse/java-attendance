package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public enum CampusTime {
    MONDAY(LocalTime.of(13, 0), List.of(DayOfWeek.MONDAY)),
    REST_DAY(LocalTime.of(10, 0), List.of(
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    ));

    private final LocalTime openTime;
    private final List<DayOfWeek> dayOfWeeks;

    CampusTime(LocalTime openTime, List<DayOfWeek> dayOfWeeks) {
        this.openTime = openTime;
        this.dayOfWeeks = dayOfWeeks;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public boolean contain(DayOfWeek otherDay) {
        return dayOfWeeks.contains(otherDay);
    }
}
