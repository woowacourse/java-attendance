package domain;

import java.util.List;

import static domain.December.*;

public enum StartTime {

    TEN(10, List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)),
    THIRTEEN(13, List.of(MONDAY));

    private final int hour;
    private final List<December> days;

    StartTime(int hour, List<December> days) {
        this.hour = hour;
        this.days = days;
    }

    public static int findStartTime(int dayOfMonth) {
        December dayOfWeek = December.findDayOfWeek(dayOfMonth);
        return getStartingTime(dayOfWeek);
    }

    private static int getStartingTime(December dayOfMonth) {
        for (StartTime startTime : StartTime.values()) {
            if (startTime.days.contains(dayOfMonth))
                return startTime.hour;
        }
        throw new IllegalArgumentException();
    }
}
