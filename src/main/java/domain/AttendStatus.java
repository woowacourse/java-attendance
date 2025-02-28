package domain;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.BiFunction;

public enum AttendStatus {
    ATTEND((localTime, timeBoundary)
            -> localTime != null && (localTime.equals(timeBoundary.lateTime()) || localTime.isBefore(
            timeBoundary.lateTime()))),
    LATE((localTime, timeBoundary)
            -> localTime != null
            && localTime.isAfter(timeBoundary.lateTime()) && !localTime.isAfter(timeBoundary.absenceTime())),
    ABSENCE((localTime, timeBoundary)
            -> localTime == null || localTime.isAfter(timeBoundary.absenceTime()));

    private final BiFunction<LocalTime, TimeBoundary, Boolean> condition;

    AttendStatus(final BiFunction<LocalTime, TimeBoundary, Boolean> condition) {
        this.condition = condition;
    }

    public static AttendStatus checkAttendStatus(Attend attend) {
        TimeBoundary timeBoundary = TimeBoundary.createTimeBoundary(attend.getDate());
        return Arrays.stream(AttendStatus.values())
                .filter(attendStatus -> attendStatus.condition.apply(attend.getTime(), timeBoundary))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 상태 판정 실패"));
    }
}
