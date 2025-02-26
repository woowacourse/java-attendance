package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum ClassTime {
    MONDAY(LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    ;

    private final LocalTime classStartTime;
    private final LocalTime classEndTime;

    ClassTime(LocalTime classStartTime, LocalTime classEndTime) {
        this.classStartTime = classStartTime;
        this.classEndTime = classEndTime;
    }

    public static LocalTime getClassStartTime(LocalDate checkInDate) {
        String dayOfWeek = checkInDate.getDayOfWeek().toString();
        ClassTime classTime = ClassTime.valueOf(dayOfWeek);
        return classTime.classStartTime;
    }
}
