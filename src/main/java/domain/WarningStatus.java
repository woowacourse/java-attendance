package domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum WarningStatus {
    CLEAR((totalAbsenceCount) -> totalAbsenceCount < 2),
    WARNING((totalAbsenceCount) -> totalAbsenceCount == 2),
    INTERVIEW((totalAbsenceCount) -> totalAbsenceCount >= 3 && totalAbsenceCount <= 5),
    EXPEL((totalAbsenceCount) -> totalAbsenceCount > 5);

    private final Predicate<Long> matchCondition;

    WarningStatus(Predicate<Long> matchCondition) {
        this.matchCondition = matchCondition;
    }

    public static WarningStatus judgeWarningStatus(long totalAbsenceCount) {
        return Arrays.stream(WarningStatus.values())
                .filter(warningStatus -> warningStatus.matchCondition.test(totalAbsenceCount))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("경고 상태 판정 실패"));
    }
}
