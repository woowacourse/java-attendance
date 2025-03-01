package domain;

import java.time.LocalTime;

public enum DayOfWeek {
    MONDAY(LocalTime.of(13, 0), LocalTime.of(18, 0), 1),
    TUESDAY(LocalTime.of(10, 0), LocalTime.of(18, 0), 2),
    WEDNESDAY(LocalTime.of(10, 0), LocalTime.of(18, 0), 3),
    THURSDAY(LocalTime.of(10, 0), LocalTime.of(18, 0), 4),
    FRIDAY(LocalTime.of(10, 0), LocalTime.of(18, 0), 5),
    SATURDAY(LocalTime.of(10, 0), LocalTime.of(18, 0), 6),
    SUNDAY(LocalTime.of(10, 0), LocalTime.of(18, 0), 7);

    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int dayOfWeekCode;

    DayOfWeek(LocalTime openTime, LocalTime closeTime, int dayOfWeekCode) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.dayOfWeekCode = dayOfWeekCode;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public int getDayOfWeekCode() {
        return dayOfWeekCode;
    }
}
