package domain.policy;

import java.time.LocalTime;
import view.ErrorMessage;

public enum TimePolicy {
    OPERATING_START(LocalTime.of(8, 0)),
    OPERATING_END(LocalTime.of(23, 0)),

    SPECIAL_ATTEND_DEAD_LINE(LocalTime.of(10, 5)),
    SPECIAL_LATE_DEAD_LINE(LocalTime.of(10, 30)),

    NORMAL_ATTEND_DEAD_LINE(LocalTime.of(13, 5)),
    NORMAL_LATE_DEAD_LINE(LocalTime.of(13, 30));

    private final LocalTime time;

    TimePolicy(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    // 시간 정책 1. 입력 시간은 운영시간의 범위를 벗어나서는 안된다.
    public static void validateTimeIsInTheRangeOfOperation(LocalTime time) {
        if (time.isBefore(OPERATING_START.getTime()) || time.isAfter(OPERATING_END.getTime())) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_TIME_IS_NOT_A_CAMPUS_OPERATING_TIME.getFormat());
        }
    }
}