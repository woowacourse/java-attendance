package attendance.domain;

import java.time.DayOfWeek;
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

    public static boolean isAttend(DayOfWeek inputDayOfWeek, LocalTime inputTime) {
        if (inputDayOfWeek == DayOfWeek.MONDAY) {
            return !inputTime.isBefore(MONDAY_ATTEND.time) && inputTime.isBefore(MONDAY_LATE.time);
        }

        return !inputTime.isBefore(GENERAL_ATTEND.time) && inputTime.isBefore(GENERAL_LATE.time);
    }

    public static boolean isLate(DayOfWeek inputDayOfWeek,LocalTime inputTime) {
        if (inputDayOfWeek == DayOfWeek.MONDAY) {
            return !inputTime.isBefore(MONDAY_LATE.time) && inputTime.isBefore(MONDAY_ABSENT.time);
        }

        return !inputTime.isBefore(GENERAL_LATE.time) && inputTime.isBefore(GENERAL_ABSENT.time);
    }
}
