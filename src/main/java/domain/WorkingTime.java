package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public enum WorkingTime {

    MONDAY(LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    ;

    private final LocalTime startTime;
    private final LocalTime endTime;

    WorkingTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static int getMinute(LocalDateTime checkInTime) {
        String name = checkInTime.getDayOfWeek().name();
        WorkingTime workingTime = WorkingTime.valueOf(name);


        LocalTime startTime = workingTime.startTime;

        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.from(checkInTime), startTime);

        return Math.toIntExact(ChronoUnit.MINUTES.between(startDateTime, checkInTime));
    }
}
