package attendance.domain;

import java.time.LocalTime;

public enum AttendanceStatus {

    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String value;

    private static final LocalTime MONDAY_LATE = LocalTime.of(13, 6);
    private static final LocalTime MONDAY_ABSENT = LocalTime.of(13, 31);
    private static final LocalTime OTHER_DAY_LATE = LocalTime.of(10, 6);
    private static final LocalTime OTHER_DAY_ABSENT = LocalTime.of(10, 31);

    AttendanceStatus(final String value) {

        this.value = value;
    }

    public static AttendanceStatus getAttendanceStatus(AttendanceTime attendanceTime) {

        if (attendanceTime.isDefaultAbsent()) {
            return ABSENT;
        }
        if (attendanceTime.isMonday()) {
            return calculateAttendanceStatus(attendanceTime, MONDAY_LATE, MONDAY_ABSENT);
        }
        return calculateAttendanceStatus(attendanceTime, OTHER_DAY_LATE, OTHER_DAY_ABSENT);
    }

    private static AttendanceStatus calculateAttendanceStatus(AttendanceTime attendanceTime, LocalTime lateTime,
                                                              LocalTime absentTime) {

        if (attendanceTime.isBefore(lateTime)) {
            return ATTEND;
        }
        if (attendanceTime.isBefore(absentTime)) {
            return LATE;
        }
        return ABSENT;
    }

    public String getValue() {

        return value;
    }
}
