package attendance.model;

import java.time.LocalTime;

public enum Attendance {
    PRESENT("출석", 0),
    LATE("지각", 5),
    ABSENT("결석", 30),
    ;

    private final String title;
    private final int lateMinute;

    Attendance(String title, int lateMinute) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }
}
