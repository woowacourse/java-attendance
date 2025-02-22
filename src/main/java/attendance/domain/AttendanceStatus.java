package attendance.domain;

import java.time.LocalDateTime;

public enum AttendanceStatus {

    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String value;

    AttendanceStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AttendanceStatus getAttendanceStatusWithCondition(AttendanceTime attendanceTime, int hour,
                                                                    int lateMinute,
                                                                    int absentMinute) {

        LocalDateTime attendDeadlineTime = LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(),
                attendanceTime.getDay(), hour, lateMinute);
        if (!attendanceTime.isAfter(attendDeadlineTime)) {
            return ATTEND;
        }

        LocalDateTime lateDeadlineTime = LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(),
                attendanceTime.getDay(), hour, absentMinute);
        if (!attendanceTime.isAfter(lateDeadlineTime)) {
            return LATE;
        }

        return ABSENT;
    }
}
