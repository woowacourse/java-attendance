package domain;

import java.time.LocalTime;

public class TimeAndStatus {

    private final LocalTime time;
    private final AttendanceStatus status;

    public TimeAndStatus(LocalTime time, String dayOfWeek) {
        this.time = time;
        this.status = checkStatus(dayOfWeek);
    }

    private AttendanceStatus checkStatus(String dayOfWeek) {
        if (dayOfWeek.equals("월")) {
            if (time.isAfter(LocalTime.of(13, 30))) {
                return AttendanceStatus.ABSENCE;
            }
            if (time.isAfter(LocalTime.of(13, 5))) {
                return AttendanceStatus.LATENESS;
            }
            return AttendanceStatus.ATTENDANCE;
        }
        if (time.isAfter(LocalTime.of(10, 30))) {
            return AttendanceStatus.ABSENCE;
        }
        if (time.isAfter(LocalTime.of(10, 5))) {
            return AttendanceStatus.LATENESS;
        }
        return AttendanceStatus.ATTENDANCE;

    }

    public LocalTime getTime() {
        return time;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
