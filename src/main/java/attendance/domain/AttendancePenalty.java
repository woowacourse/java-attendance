package attendance.domain;

import attendance.common.Constants;

import static attendance.common.Constants.COUNSELING_MINIMUM;
import static attendance.common.Constants.EXPULSION_MINIMUM;
import static attendance.common.Constants.WARING_MAXIMUM;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public enum AttendancePenalty {

    EXPULSION("제적", lateCont -> lateCont >= EXPULSION_MINIMUM),
    COUNSELING("면담", lateCount -> lateCount >= COUNSELING_MINIMUM && lateCount < EXPULSION_MINIMUM),
    WARNING("경고", lateCount -> lateCount == WARING_MAXIMUM),
    NONE("없음", lateCount -> lateCount < WARING_MAXIMUM);

    private final String message;
    private final Predicate<Integer> rule;

    AttendancePenalty(String message, Predicate<Integer> rule) {
        this.message = message;
        this.rule = rule;
    }

    public static AttendancePenalty find(Map<AttendanceStatus, Integer> statusCount) {
        return find(statusCount.get(AttendanceStatus.ABSENCE), statusCount.get(AttendanceStatus.LATE));
    }

    public static AttendancePenalty find(int absenceCount, int lateCount) {
        int totalCount = absenceCount + lateCount / Constants.LATE_TO_ABSENCE_RATIO;

        return Arrays.stream(values())
                .filter(penalty -> penalty.rule.test(totalCount))
                .findFirst()
                .orElse(NONE);
    }

    public String getMessage() {
        return message;
    }
}
