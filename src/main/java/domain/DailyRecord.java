package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DailyRecord {

    private final LocalTime time;
    private final AttendanceStatus status;

    public DailyRecord(LocalTime time, DayOfWeek dayOfWeek) {
        this.time = time;
        this.status = AttendanceStatus.of(time, dayOfWeek);
    }

    public LocalTime getTime() {
        return time;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
