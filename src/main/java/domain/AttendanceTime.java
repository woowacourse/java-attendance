package domain;

import java.time.LocalTime;

public class AttendanceTime {

    private static final LocalTime MONDAY_SCHOOL_OPEN_TIME = LocalTime.of(13, 0);
    private static final LocalTime MONDAY_SCHOOL_CLOSE_TIME = LocalTime.of(18, 0);
    private static final LocalTime NORMAL_SCHOOL_OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime NORMAL_SCHOOL_CLOSE_TIME = LocalTime.of(18, 0);

    private final LocalTime attendanceTime;

    public AttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    private LocalTime schoolStartDay(boolean isMonday) {
        if (isMonday) {
            return MONDAY_SCHOOL_OPEN_TIME;
        }
        return NORMAL_SCHOOL_OPEN_TIME;
    }

    public int minuteFromSchoolStartTime(boolean monday) {
        LocalTime schoolStartTime = schoolStartDay(monday);
        var attendanceMinute = attendanceTime.toSecondOfDay() / 60;
        var schoolStartTimeMinute = schoolStartTime.toSecondOfDay() / 60;
        return attendanceMinute - schoolStartTimeMinute;
    }
}
