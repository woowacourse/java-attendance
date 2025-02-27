package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import attendance.exception.AttendanceArgumentException;

public enum Schedule {
    DEFAULT(LocalTime.of(10, 0), Collections.emptySet()),
    MONDAY(LocalTime.of(13, 0), Set.of(DayOfWeek.MONDAY)),
    CAMPUS_OPEN(LocalTime.of(8, 0), Collections.emptySet()),
    CAMPUS_CLOSE(LocalTime.of(23, 0), Collections.emptySet());

    private static final String ERROR_OUT_OF_SCHOOL_SCHEDULE = "등교시간에만 출석 가능합니다.";
    private final LocalTime time;
    private final Set<DayOfWeek> dayOfWeeks;

    Schedule(LocalTime time, Set<DayOfWeek> dayOfWeeks) {
        this.time = time;
        this.dayOfWeeks = dayOfWeeks;
    }

    public LocalTime getTime() {
        return time;
    }

    public static Schedule getScheduleOnDay(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();

        return Arrays.stream(values())
            .filter(schedule -> schedule.isStartingOn(dayOfWeek))
            .findFirst()
            .orElse(DEFAULT);
    }

    private boolean isStartingOn(DayOfWeek dayOfWeek) {
        return dayOfWeeks.contains(dayOfWeek);
    }

    public static void validateCampusSchedule(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        if (time.isBefore(CAMPUS_OPEN.getTime()) || time.isAfter(CAMPUS_CLOSE.getTime())) {
            throw new AttendanceArgumentException(ERROR_OUT_OF_SCHOOL_SCHEDULE);
        }
    }
}
