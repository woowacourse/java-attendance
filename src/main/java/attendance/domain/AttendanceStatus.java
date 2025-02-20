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

    public static String checkStatusWithCondition(Time attendanceTime, int hour, int lateMinute, int absentMinute) {

        if (!attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDay(),
                        hour,
                        lateMinute))) {
            return ATTEND.getValue();
        }

        if (!attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDay(),
                        hour,
                        absentMinute))) {
            return LATE.getValue();
        }

        return ABSENT.getValue();
    }
}
