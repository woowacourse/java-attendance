package domain;

import except.AttendanceException;
import java.time.LocalTime;

public class AttendanceTime {

    private static final LocalTime MONDAY_SCHOOL_ATTENDANCE_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime MONDAY_SCHOOL_ATTENDANCE_TIME = LocalTime.of(10, 0);
    private static final LocalTime SCHOOL_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime SCHOOL_CLOSE_TIME = LocalTime.of(23, 0);
    private static final String SCHOOL_DOENST_OPEN = "학교 운영 시간은 8시부터 23시까지 입니다.";

    private final LocalTime attendanceTime;

    public AttendanceTime(LocalTime attendanceTime, AttendanceDate attendanceDate) {
        validateAttendanceTime(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    private void validateAttendanceTime(LocalTime attendanceTime) {
        if (attendanceTime.isBefore(SCHOOL_OPEN_TIME)) {
            throw new AttendanceException(SCHOOL_DOENST_OPEN);
        }
        if (attendanceTime.isAfter(SCHOOL_CLOSE_TIME)) {
            throw new AttendanceException(SCHOOL_DOENST_OPEN);
        }
    }

    private LocalTime schoolStartDay(boolean isMonday) {
        if (isMonday) {
            return MONDAY_SCHOOL_ATTENDANCE_START_TIME;
        }
        return MONDAY_SCHOOL_ATTENDANCE_TIME;
    }

    public int minuteFromSchoolStartTime(boolean monday) {
        LocalTime schoolStartTime = schoolStartDay(monday);
        var attendanceMinute = attendanceTime.toSecondOfDay() / 60;
        var schoolStartTimeMinute = schoolStartTime.toSecondOfDay() / 60;
        return attendanceMinute - schoolStartTimeMinute;
    }
}
