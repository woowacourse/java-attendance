package domain;

import static domain.AttendanceStatus.checkStatus;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DailyRecord {

    private final LocalTime time;
    private final AttendanceStatus status;

    public DailyRecord(LocalTime time, DayOfWeek dayOfWeek) {
        this.time = time;
        this.status = checkStatus(time, dayOfWeek);
    }

    public LocalTime getTime() {
        return time;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
