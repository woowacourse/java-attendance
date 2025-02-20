package attendance.model;

import java.time.LocalDateTime;

public enum Attendance {
    출석(0),
    지각(5),
    결석(30),
    ;

    private final int lateMinute;

    Attendance(int lateMinute) {
        this.lateMinute = lateMinute;
    }

    public static Attendance from(LocalDateTime dateTime) {
        long duration = WoowaDurationTime.calculateDuration(dateTime);
        if (duration > 결석.lateMinute) {
            return 결석;
        }
        if (duration > 지각.lateMinute) {
            return 지각;
        }
        return 출석;
    }
}
