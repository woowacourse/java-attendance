package attendance.domain;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.BiPredicate;

public enum AttendanceStatus {
    ATTEND("출석", (arrivalTime, startTime) -> {
        Duration duration = Duration.between(arrivalTime, startTime);
        return duration.isZero() || duration.isNegative() && duration.abs().toMinutes() <= 5;
    }),
    LATE("지각", (arrivalTime, startTime) -> {
        Duration duration = Duration.between(arrivalTime, startTime);
        return duration.isNegative() && duration.abs().toMinutes() > 5 && duration.abs().toMinutes() <= 30;
    }),
    ABSENT("결석", (arrivalTime, startTime) -> {
        Duration duration = Duration.between(arrivalTime, startTime);
        return duration.isNegative() && duration.abs().toMinutes() > 30;
    });

    private final String status;
    private final BiPredicate<LocalTime, LocalTime> timeCondition;

    AttendanceStatus(String status, BiPredicate<LocalTime, LocalTime> timeCondition) {
        this.status = status;
        this.timeCondition = timeCondition;
    }

    public static AttendanceStatus getStatusByTime(LocalTime arrivalTime, LocalTime startTime) {

        return Arrays.stream(AttendanceStatus.values())
                .filter(attendanceStatus -> attendanceStatus.matches(arrivalTime, startTime))
                .findFirst()
                .orElse(ABSENT);
    }

    private boolean matches(LocalTime arrivalTime, LocalTime startTime) {
        return timeCondition.test(arrivalTime, startTime);
    }
}
