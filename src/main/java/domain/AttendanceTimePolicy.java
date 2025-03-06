package domain;

import java.time.LocalTime;

public enum AttendanceTimePolicy {
    START(LocalTime.of(8, 0)),
    END(LocalTime.of(23, 0));

    private final LocalTime operatingTime;

    AttendanceTimePolicy(LocalTime attendanceTime) {
        this.operatingTime = attendanceTime;
    }

    public static boolean isOperatingTime(LocalTime attendanceTime) {
        return (attendanceTime.isAfter(START.operatingTime) && attendanceTime.isBefore(END.operatingTime));
    }
}
