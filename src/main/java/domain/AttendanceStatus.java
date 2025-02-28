package domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceStatus {
    PRESENT("출석", 0),
    TARDY("지각", 5),
    ABSENT("결석", 30);

    private final String name;
    private final int minutesAfter;

    AttendanceStatus(String name, int minutesAfter) {
        this.name = name;
        this.minutesAfter = minutesAfter;
    }

    public static AttendanceStatus getStatus(DayOfWeek dayOfWeek, LocalTime time) {
        LocalTime startTime = ClassSchedule.getStartTimeOf(dayOfWeek);
        Duration lateness = Duration.between(startTime, time);
        return Arrays.stream(AttendanceStatus.values())
                .filter(status -> lateness.toMinutes() > status.minutesAfter)
                .reduce((first, second) -> second)
                .orElse(PRESENT);
    }

    public String getName() {
        return name;
    }
}
