package domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");
    private static final int LATE_MIN = 5;
    private static final int ABSENCE_MIN = 30;
    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus calculateAttendanceStatus(LocalTime attendanceTime,
                                                             LocalTime schoolAttendanceStartTime) {
        if (attendanceTime.isAfter(schoolAttendanceStartTime.plusMinutes(ABSENCE_MIN))) {
            return ABSENCE;
        }
        if (attendanceTime.isAfter(schoolAttendanceStartTime.plusMinutes(LATE_MIN))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    public String getStatus() {
        return status;
    }
}

