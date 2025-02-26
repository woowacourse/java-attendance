package attendance.domain;

import java.time.LocalTime;

public enum EducationTime {
    MONDAY_ATTEND_START(LocalTime.of(13, 0)),
    MONDAY_ATTEND_END(LocalTime.of(13, 5)),
    MONDAY_LATE_START(LocalTime.of(13, 6)),
    MONDAY_LATE_END(LocalTime.of(13, 30)),
    MONDAY_ABSENT_START(LocalTime.of(13, 31)),
    GENERAL_ATTEND_START(LocalTime.of(10, 0)),
    GENERAL_ATTEND_END(LocalTime.of(10, 5)),
    GENERAL_LATE_START(LocalTime.of(10, 6)),
    GENERAL_LATE_END(LocalTime.of(10, 30)),
    GENERAL_ABSENT_START(LocalTime.of(10, 31))
    ;

    private final LocalTime time;

    EducationTime(LocalTime time) {
        this.time = time;
    }

    public static boolean isAttendOfMonday(LocalTime inputTime) {
        return !inputTime.isBefore(MONDAY_ATTEND_START.time) && !inputTime.isAfter(MONDAY_ATTEND_END.time);
    }
}
