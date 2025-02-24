package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum EducationTime {
    MONDAY(LocalTime.of(13, 0, 0)),
    WEEKDAY(LocalTime.of(10, 0, 0));

    private final LocalTime startTime;

    EducationTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public static int calculateOverTime(LocalDateTime attendanceTime) {
        DayOfWeek dayOfWeek = attendanceTime.getDayOfWeek();
        LocalTime startTime = WEEKDAY.startTime;

        if (dayOfWeek == DayOfWeek.MONDAY) {
            startTime = MONDAY.startTime;
        }

        int secondsDifference = attendanceTime.toLocalTime().toSecondOfDay() - startTime.toSecondOfDay();
        int overTimeMinutes = secondsDifference / 60;

        return Math.max(overTimeMinutes, 0);
    }
}
