package attendance.domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    ABSENCE(30),
    LATE(5),
    ATTENDANCE(0);

    private final int minutes;

    AttendanceStatus(int minutes) {
        this.minutes = minutes;
    }

    public static AttendanceStatus decideStatus(LocalTime time, LocalTime baseSchedule) {
        for (AttendanceStatus status : values()) {
            if (status.isAfterThreshold(time, baseSchedule)) {
                return status;
            }
        }
        return ATTENDANCE;
    }

    private boolean isAfterThreshold(LocalTime time, LocalTime baseSchedule) {
        return time.isAfter(baseSchedule.plusMinutes(this.minutes));
    }
}
