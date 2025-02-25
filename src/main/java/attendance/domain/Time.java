package attendance.domain;

import java.time.LocalDateTime;

public class Time {

    private final LocalDateTime attendanceTime;

    public Time(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }
}
