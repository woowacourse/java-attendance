package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {

    OK(5),
    LATE(5),
    ABSENT(30);

    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime TUESDAY_TO_FRIDAY_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime END_TIME = LocalTime.of(18, 0);

    private final int deadLineMinute;

    AttendanceStatus(final int deadLineMinute) {
        this.deadLineMinute = deadLineMinute;
    }

    public static AttendanceStatus findByAttendanceDateTime(final LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        if (attendanceDateTime.toLocalDate().getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return findStatusByStartTime(MONDAY_START_TIME, attendanceTime);
        }
        return findStatusByStartTime(TUESDAY_TO_FRIDAY_START_TIME, attendanceTime);
    }

    private static AttendanceStatus findStatusByStartTime(final LocalTime startTime, final LocalTime attendanceTime) {
        int result = (attendanceTime.getHour() - startTime.getHour()) * 60
                + (attendanceTime.getMinute() - startTime.getMinute());
        if (result > ABSENT.deadLineMinute) {
            return ABSENT;
        }
        if (result > LATE.deadLineMinute) {
            return LATE;
        }
        return OK;
    }

}
