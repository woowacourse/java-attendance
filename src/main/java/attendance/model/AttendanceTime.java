package attendance.model;

import java.time.Duration;
import java.time.LocalTime;

public record AttendanceTime(
        LocalTime localTime
) {
    public long computeMinuteDelta(LocalTime startTime) {
        return Duration.between(startTime, localTime).toMinutes();
    }
}
