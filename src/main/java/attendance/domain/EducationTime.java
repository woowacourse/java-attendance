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
            return isBetween(inputTime, MONDAY_ATTEND.time, MONDAY_LATE.time);
        }

        return isBetween(inputTime, GENERAL_ATTEND.time, GENERAL_LATE.time);
    }

    public static boolean isLate(DayOfWeek inputDayOfWeek,LocalTime inputTime) {
        if (inputDayOfWeek == DayOfWeek.MONDAY) {
            return isBetween(inputTime, MONDAY_LATE.time, MONDAY_ABSENT.time);
        }

        return isBetween(inputTime, GENERAL_LATE.time, GENERAL_ABSENT.time);
    }

    private static boolean isBetween(LocalTime inputTime, LocalTime startTime, LocalTime endTime) {
        return !inputTime.isBefore(startTime) && inputTime.isBefore(endTime);
    }
}
