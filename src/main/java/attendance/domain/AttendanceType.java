package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceType {
    SAFE("출석"),
    LATE("지각"),
    ABSENT("결석"),
    FREE("자유");

    private static final int MONDAY_START_HOUR = 13;
    private static final int WEEKDAY_START_HOUR = 10;
    private static final int LATE_START_MINUTE = 5;
    private static final int ABSENT_START_MINUTE = 30;

    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private final String attendanceType;

    AttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }

    public static AttendanceType of(final AttendanceDate attendanceDate, final AttendanceTime attendanceTime) {
        DayOfWeek dayOfWeek = attendanceDate.getAttendanceDate().getDayOfWeek();
        LocalTime time = attendanceTime.getAttendanceTime();

        if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            return FREE;
        }
        if (dayOfWeek.equals(DayOfWeek.MONDAY)) {
            return checkAttendanceType(time, MONDAY_START_HOUR);
        }
        return checkAttendanceType(time, WEEKDAY_START_HOUR);
    }

    private static AttendanceType checkAttendanceType(final LocalTime time, final int startHour) {
        if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
            return ABSENT;
        }
        if (time.isAfter(LocalTime.of(startHour, ABSENT_START_MINUTE))) {
            return ABSENT;
        }
        if (time.isAfter(LocalTime.of(startHour, LATE_START_MINUTE))) {
            return LATE;
        }
        return SAFE;
    }

    @Override
    public String toString() {
        return attendanceType;
    }
}
