package attendance.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public enum AttendancePenalty {

    EXPULSION("제적", lateCont -> lateCont >= Constants.EXPULSION_MINIMUM),
    COUNSELING("면담", lateCount -> lateCount >= Constants.COUNSELING_MINIMUM && lateCount < Constants.EXPULSION_MINIMUM),
    WARNING("경고", lateCount -> lateCount == Constants.WARING_MAXIMUM),
    NONE("없음", lateCount -> lateCount < Constants.WARING_MAXIMUM);

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
        int totalCount = absenceCount + lateCount / PenaltyCrew.LATE_TO_ABSENCE_RATIO;

        return Arrays.stream(values())
                .filter(penalty -> penalty.rule.test(totalCount))
                .findFirst()
                .orElse(NONE);
    }

    public String getMessage() {
        return message;
    }

    private static class Constants {
        private static final int EXPULSION_MINIMUM = 6;
        private static final int COUNSELING_MINIMUM = 3;
        private static final int WARING_MAXIMUM = 2;
    }
}
