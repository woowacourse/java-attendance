package attendance.domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum ExpulsionStatus {

    EXPULSION("제적", (absentCount) -> (absentCount < Integer.MAX_VALUE) && (absentCount >= 6)),
    INTERVIEW("면담", (absentCount) -> (absentCount < 6) && (absentCount >= 3)),
    WARNING("경고", (absentCount) -> absentCount == 2),
    NONE("없음", (absentCount) -> (absentCount < 2) && (absentCount >= 0));

    private final String text;
    private final Predicate<Integer> condition;

    ExpulsionStatus(final String text, final Predicate<Integer> condition) {
        this.text = text;
        this.condition = condition;
    }

    public static ExpulsionStatus findStatusByAbsentCount(final int totalAbsentCount) {
        return Arrays.stream(values())
                .filter(expulsionStatus -> expulsionStatus.condition.test(totalAbsentCount))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 결석 횟수입니다."));
    }

    public String getText() {
        return text;
    }
}
