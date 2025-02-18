package attendance.model;

import java.time.LocalTime;

public enum WoowaDayOfWeek {
    월요일(LocalTime.of(13, 0), LocalTime.of(18, 0)),
    화요일(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    수요일(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    목요일(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    금요일(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    토요일(null, null),
    일요일(null, null),
    ;

    private final LocalTime startTime;
    private final LocalTime endTime;

    WoowaDayOfWeek(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
