package attendance;

import java.time.LocalTime;

public class AttendanceTime {
    private final LocalTime time;

    public AttendanceTime(LocalTime time) {
        if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0))) {
            throw new IllegalArgumentException();
        }
        this.time = time;
    }
}
