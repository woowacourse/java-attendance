package attendance.domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE(0),
    LATE(5),
    ABSENCE(30);

    private int value;

    AttendanceStatus(int value) {
        this.value = value;
    }

    private int getValue() {
        return value;
    }

    public static AttendanceStatus judgeStatus(LocalTime time, LocalTime schedule) {
        if (time.isAfter(schedule.plusMinutes(ABSENCE.getValue()))) {
            return AttendanceStatus.ABSENCE;
        }
        if (time.isAfter(schedule.plusMinutes(LATE.getValue()))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }
}
