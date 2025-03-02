package attendance.domain;

import static java.time.DayOfWeek.MONDAY;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Predicate;

public enum AttendanceStatus {
    ABSENCE("결석", time -> time.isAfter(LocalTime.of(10, 30)),
            time -> time.isAfter(LocalTime.of(13, 30))),
    LATENESS("지각", time -> time.isAfter(LocalTime.of(10, 5)),
            time -> time.isAfter(LocalTime.of(13, 5))),
    PRESENT("출석", time -> true, time -> true);

    private final String status;
    private final Predicate<LocalTime> regularCondition;
    private final Predicate<LocalTime> mondayCondition;

    AttendanceStatus(String status, Predicate<LocalTime> regularCondition, Predicate<LocalTime> mondayCondition) {
        this.status = status;
        this.regularCondition = regularCondition;
        this.mondayCondition = mondayCondition;
    }

    public static AttendanceStatus of(LocalDate date, LocalTime time) {
        if (isMonday(date)) {
            return calculateMondayStatus(time);
        }
        return calculateRegularStatus(time);
    }

    private static AttendanceStatus calculateMondayStatus(LocalTime time) {
        return Arrays.stream(values())
                .filter(status -> status.mondayCondition.test(time))
                .findFirst()
                .orElseThrow();
    }

    private static AttendanceStatus calculateRegularStatus(LocalTime time) {
        return Arrays.stream(values())
                .filter(status -> status.regularCondition.test(time))
                .findFirst()
                .orElseThrow();
    }

    private static boolean isMonday(LocalDate date) {
        return date.getDayOfWeek() == MONDAY;
    }

    public String getStatus() {
        return this.status;
    }
}
