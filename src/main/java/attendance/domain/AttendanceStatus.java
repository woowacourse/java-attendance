package attendance.domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석", 30),
    LATE("지각", 5),
    ABSENCE("결석", 0);

    private final String status;
    private final int minutes;

    AttendanceStatus(String status, int minutes) {
        this.status = status;
        this.minutes = minutes;
    }

    public String getStatus() {
        return status;
    }

    public static AttendanceStatus decideStatus(LocalTime time, LocalTime baseSchedule) {
        for (AttendanceStatus status : values()) {
            if (status.isAfterThreshold(time, baseSchedule)) {
                return status;
            }
        }
        return ATTENDANCE; // 기본값
    }

    private boolean isAfterThreshold(LocalTime time, LocalTime baseSchedule) {
        return time.isAfter(baseSchedule.plusMinutes(this.minutes));
    }
}
