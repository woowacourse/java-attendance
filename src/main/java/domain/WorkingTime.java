package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public enum WorkingTime {
    MONDAY(13, 0, 18, 0),
    TUESDAY(10, 0, 18, 0),
    WEDNESDAY(10, 0, 18, 0),
    THURSDAY(10, 0, 18, 0),
    FRIDAY(10, 0, 18, 0),
    ;

    private final int startHour;
    private final int startMinute;
    private final int endHour;
    private final int endMinute;

    WorkingTime(int startHour,
                int startMinute,
                int endHour,
                int endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public static int getMinute(LocalDateTime checkInTime) {
        String name = checkInTime.getDayOfWeek().name();
        WorkingTime workingTime = WorkingTime.valueOf(name);

        int hour = workingTime.startHour;
        int minute = workingTime.startMinute;

        LocalDateTime startTime = LocalDateTime.of(LocalDate.from(checkInTime), LocalTime.of(hour, minute));

        return Math.toIntExact(ChronoUnit.MINUTES.between(startTime, checkInTime));
    }
}
