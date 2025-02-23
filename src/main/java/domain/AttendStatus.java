package domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum AttendStatus {
    ATTEND((attend) -> attend.hasTime() && attend.isBefore(OperationTime.LATE_TIME.getTime())),
    LATE((attend) -> attend.hasTime()
            && (attend.isEqual(OperationTime.LATE_TIME.getTime()) || attend.isAfter(OperationTime.LATE_TIME.getTime()))
            && attend.isBefore(OperationTime.ABSENCE_TIME.getTime())),
    ABSENCE((attend) -> !attend.hasTime()
            || attend.isEqual(OperationTime.ABSENCE_TIME.getTime())
            || attend.isAfter(OperationTime.ABSENCE_TIME.getTime()));

    private final Predicate<Attend> matchCondition;

    AttendStatus(Predicate<Attend> matchCondition) {
        this.matchCondition = matchCondition;
    }

    public static AttendStatus findAttendStatus(Attend attend) {
        return Arrays.stream(AttendStatus.values())
                .filter(attendStatus -> attendStatus.matchCondition.test(attend))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("출석 상태 판정 실패"));
    }
}
