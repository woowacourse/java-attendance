package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum EducationTime {

    MONDAY_ATTEND(LocalTime.of(13, 0)),
    MONDAY_LATE(LocalTime.of(13, 5)),
    MONDAY_ABSENT(LocalTime.of(13, 30)),
    GENERAL_ATTEND(LocalTime.of(10, 0)),
    GENERAL_LATE(LocalTime.of(10, 5)),
    GENERAL_ABSENT(LocalTime.of(10, 30))
    ;

    private final LocalTime time;

    EducationTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public static boolean isBetweenAttendTime(DayOfWeek inputDayOfWeek, LocalTime inputTime) {
        if (inputDayOfWeek == DayOfWeek.MONDAY) {
            return !inputTime.isBefore(CampusOperatingTime.OPEN.getTime()) && !inputTime.isAfter(MONDAY_LATE.time);
        }

        return !inputTime.isBefore(CampusOperatingTime.OPEN.getTime()) && !inputTime.isAfter(GENERAL_LATE.time);
    }

    public static boolean isBetweenLateTime(DayOfWeek inputDayOfWeek,LocalTime inputTime) {
        if (inputDayOfWeek == DayOfWeek.MONDAY) {
            return inputTime.isAfter(MONDAY_LATE.time) && !inputTime.isAfter(MONDAY_ABSENT.time);
        }

        return inputTime.isAfter(GENERAL_LATE.time) && !inputTime.isAfter(GENERAL_ABSENT.time);
    }
}
