package attendance.domain;

import attendance.exception.ExceptionMessage;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum CampusSchedule {
    CAMPUS_OPEN_TIME(LocalTime.of(8, 0, 0)),
    CAMPUS_CLOSE_TIME(LocalTime.of(23, 0, 0)),
    MONDAY_EDUCATION_START_TIME(LocalTime.of(13, 0, 0)),
    MONDAY_EDUCATION_END_TIME(LocalTime.of(18, 0, 0)),
    NOT_MONDAY_EDUCATION_START_TIME(LocalTime.of(10, 0, 0)),
    NOT_MONDAY_EDUCATION_END_TIME(LocalTime.of(18, 0, 0));

    private final LocalTime time;

    CampusSchedule(LocalTime time) {
        this.time = time;
    }

    public static AttendanceType calculateAttendanceType(LocalDateTime dateTime) {
        boolean isMonday = dateTime.getDayOfWeek() == DayOfWeek.MONDAY;
        return checkAttendance(isMonday, dateTime.toLocalTime());
    }

    public LocalTime getTime() {
        return time;
    }

    private static AttendanceType checkAttendance(boolean isMonday, LocalTime time) {
        validateIsInCampusTime(time);
        if (isMonday) {
            return AttendanceType.parse(MONDAY_EDUCATION_START_TIME.getTime(), time);
        }
        return AttendanceType.parse(NOT_MONDAY_EDUCATION_START_TIME.getTime(), time);
    }

    private static void validateIsInCampusTime(LocalTime time) {
        boolean isNotInCampusTime = time.isBefore(CAMPUS_OPEN_TIME.time) || time.isAfter(CAMPUS_CLOSE_TIME.time);
        if (isNotInCampusTime) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_CAMPUS_TIME.getContent());
        }
    }
}
