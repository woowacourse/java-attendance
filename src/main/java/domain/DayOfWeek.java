package domain;

import java.time.LocalTime;

public enum DayOfWeek {
    MONDAY(LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    SATURDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    SUNDAY(LocalTime.of(10, 0), LocalTime.of(18, 0));

    private final LocalTime openTime;
    private final LocalTime closeTime;

    DayOfWeek(LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
