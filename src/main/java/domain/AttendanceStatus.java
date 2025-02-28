package domain;

import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDED("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus calculateStatus(LocalTime lateTime, LocalTime absentTime, LocalTime attendanceTime) {
        if (attendanceTime.isAfter(absentTime))
            return ABSENT;
        if (attendanceTime.isAfter(lateTime))
            return LATE;
        return ATTENDED;
    }

    public String getStatus() {
        return status;
    }
}
