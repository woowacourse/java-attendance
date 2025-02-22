package attendance.model;

import java.time.LocalDateTime;

public enum Attendance {
    PRESENT(0),
    LATE(5),
    ABSENT(30),
    ;

    private final int lateMinute;

    Attendance(int lateMinute) {
        this.lateMinute = lateMinute;
    }

    public static Attendance from(LocalDateTime dateTime) {
        long duration = WoowaDurationTime.calculateDuration(dateTime);
        if (duration > ABSENT.lateMinute) {
            return ABSENT;
        }
        if (duration > LATE.lateMinute) {
            return LATE;
        }
        return PRESENT;
    }
}
