package attendance.model;

import static attendance.error.ErrorMessage.ERROR_NOT_WOOWA_OPEN;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

public enum WoowaDurationTime {
    MON(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUE(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WED(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THU(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRI(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;

    WoowaDurationTime(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static long calculateDuration(WoowaDate woowaDate, LocalTime localTime) {
        return Duration.between(
                getStartTime(woowaDate.getDayOfWeek()), localTime
        ).toMinutes();
    }

    private static LocalTime getStartTime(DayOfWeek dayOfWeek) {
        return Arrays.stream(WoowaDurationTime.values())
                .filter(woowaDurationTime -> woowaDurationTime.dayOfWeek == dayOfWeek)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_NOT_WOOWA_OPEN)).startTime;
    }

}
