package attendance.model;

import java.time.LocalTime;

public enum Attendance {
    PRESENT(0),
    LATE(5),
    ABSENT(30),
    ;

    private final int lateMinute;

    Attendance(int lateMinute) {
        this.lateMinute = lateMinute;
    }

    public static Attendance from(WoowaDate date, LocalTime attendanceTime) {
        long duration = WoowaDurationTime.calculateDuration(date, attendanceTime);
        if (duration > ABSENT.lateMinute) {
            return ABSENT;
        }
        if (duration > LATE.lateMinute) {
            return LATE;
        }
        return PRESENT;
    }
}
