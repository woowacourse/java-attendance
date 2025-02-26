package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public enum ClassTime {
    MONDAY(LocalTime.of(13, 0)),
    TUESDAY(LocalTime.of(10, 0));

    private final LocalTime startTime;

    ClassTime(final LocalTime startTime) {
        this.startTime = startTime;
    }

    public static int calculateAttendanceDifference(final LocalDateTime attendanceDateTime) {
        DayOfWeek dayOfWeek = attendanceDateTime.toLocalDate().getDayOfWeek();
        ClassTime classTime = Arrays.stream(values())
                .filter(weekday -> weekday.name().equals(dayOfWeek.name()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return (attendanceDateTime.toLocalTime().toSecondOfDay() - classTime.startTime.toSecondOfDay()) / 60;
    }
}
