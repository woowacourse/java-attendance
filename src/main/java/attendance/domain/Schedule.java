package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum Schedule {
    CAMPUS_OPEN(LocalTime.of(8, 0)),
    CAMPUS_CLOSE(LocalTime.of(23, 0)),
    LESSON_MONDAY(LocalTime.of(13, 0)),
    LESSON_DEFAULT_DAY(LocalTime.of(10, 0));
    
    private final LocalTime time;

    Schedule(LocalTime time) {
        this.time = time;
    }

    private LocalTime getTime() {
        return time;
    }

    public static boolean isDuringCampus(LocalTime time) {
        return time.isBefore(CAMPUS_OPEN.getTime()) || time.isAfter(CAMPUS_CLOSE.getTime());
    }

    public static LocalTime getSchedule(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return LESSON_MONDAY.getTime();
        }
        return LESSON_DEFAULT_DAY.getTime();
    }
}
