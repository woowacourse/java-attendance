package attendance.domain;

import java.time.LocalTime;

public class AttendanceLocalTime extends NullableLocalTime {

    public AttendanceLocalTime(LocalTime time) {
        super(time);
    }

    @Override
    public boolean isPresent() {
        return true;
    }
}
