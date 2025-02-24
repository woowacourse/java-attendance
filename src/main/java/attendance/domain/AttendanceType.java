package attendance.domain;

import java.time.LocalTime;

public enum AttendanceType {
    ATTENDANCE(0),
    LATE(5),
    ABSENCE(30);

    private final int overTime;

    AttendanceType(int overTime) {
        this.overTime = overTime;
    }

    public static AttendanceType parse(LocalTime startTime, LocalTime arrivalTime) {
        if (arrivalTime.getHour() < startTime.getHour()) {
            return ATTENDANCE;
        }
        int overTime = arrivalTime.getMinute() - startTime.getMinute();
        if (arrivalTime.getHour() == startTime.getHour() && overTime < LATE.overTime) {
            return ATTENDANCE;
        }
        if (arrivalTime.getHour() == startTime.getHour() && overTime < ABSENCE.overTime) {
            return LATE;
        }
        return ABSENCE;
    }
}
