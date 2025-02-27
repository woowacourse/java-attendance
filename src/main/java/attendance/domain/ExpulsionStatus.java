package attendance.domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum ExpulsionStatus {

    EXPULSION((absentCount) -> (absentCount < Integer.MAX_VALUE) && (absentCount >= 6)),
    INTERVIEW((absentCount) -> (absentCount < 6) && (absentCount >= 3)),
    WARNING((absentCount) -> absentCount == 2),
    NONE((absentCount) -> (absentCount < 2) && (absentCount >= 0));

    private final Predicate<Integer> condition;

    ExpulsionStatus(final Predicate<Integer> condition) {
        this.condition = condition;
    }

    public static ExpulsionStatus findStatusByAbsentCount(final int totalAbsentCount) {
        return Arrays.stream(values())
                .filter(expulsionStatus -> expulsionStatus.condition.test(totalAbsentCount))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 결석 횟수입니다."));
    }

}
