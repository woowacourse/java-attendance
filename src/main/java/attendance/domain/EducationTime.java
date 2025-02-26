package attendance.domain;

import java.time.LocalTime;

public enum EducationTime {
    MONDAY_ATTEND(LocalTime.of(13, 0)),
    MONDAY_LATE(LocalTime.of(13, 6)),
    MONDAY_ABSENT(LocalTime.of(13, 31)),
    GENERAL_ATTEND(LocalTime.of(10, 0)),
    GENERAL_LATE(LocalTime.of(10, 6)),
    GENERAL_ABSENT(LocalTime.of(10, 31))
    ;

    private final LocalTime time;

    EducationTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public static boolean isAttendOfMonday(LocalTime inputTime) {
        return !inputTime.isBefore(MONDAY_ATTEND.time) && inputTime.isBefore(MONDAY_LATE.time);
    }

    public static boolean isLateOfMonday(LocalTime inputTime) {
        return !inputTime.isBefore(MONDAY_LATE.time) && inputTime.isBefore(MONDAY_ABSENT.time);
    }
}
