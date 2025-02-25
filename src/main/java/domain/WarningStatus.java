package domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum WarningStatus {
    CLEAR((totalAbsenceCount) -> totalAbsenceCount < WarningStatus.LATE_BOUNDARY),
    WARNING((totalAbsenceCount) -> totalAbsenceCount == WarningStatus.LATE_BOUNDARY),
    INTERVIEW((totalAbsenceCount) -> totalAbsenceCount > WarningStatus.LATE_BOUNDARY
            && totalAbsenceCount <= WarningStatus.ABSENCE_BOUNDARY),
    EXPEL((totalAbsenceCount) -> totalAbsenceCount > WarningStatus.ABSENCE_BOUNDARY);

    private static final int LATE_BOUNDARY = 2;
    private static final int ABSENCE_BOUNDARY = 5;

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
