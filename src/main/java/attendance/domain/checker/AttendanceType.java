package attendance.domain.checker;

import java.time.LocalTime;

public enum AttendanceType {
    ATTENDANCE(0, "출석"),
    LATE(5, "지각"),
    ABSENCE(30, "결석");

    private final int overTime;
    private final String name;

    AttendanceType(int overTime, String name) {
        this.overTime = overTime;
        this.name = name;
    }

    public static AttendanceType parse(LocalTime startTime, LocalTime arrivalTime) {
        if (arrivalTime.getHour() < startTime.getHour()) {
            return ATTENDANCE;
        }
        return parseByOverTime(startTime, arrivalTime);
    }

    public String getName() {
        return name;
    }

    private static AttendanceType parseByOverTime(LocalTime startTime, LocalTime arrivalTime) {
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
