package domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum ExpulsionRiskStatus {
    WARNING(2),
    INTERVIEW(3),
    EXPELLED(6),
    NORMAL(0),
    ;

    private final int lowerBoundAbsenceCount;

    ExpulsionRiskStatus(int lowerBoundAbsenceCount) {
        this.lowerBoundAbsenceCount = lowerBoundAbsenceCount;
    }

    public static ExpulsionRiskStatus of(final int absenceCount) {
        return getDescendingStatuses().stream()
                .filter(status -> absenceCount >= status.lowerBoundAbsenceCount)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("유효하지 않은 결석 횟수가 입력되었습니다."));
    }

    private static List<ExpulsionRiskStatus> getDescendingStatuses() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing(ExpulsionRiskStatus::getLowerBoundAbsenceCount).reversed())
                .toList();
    }

    private int getLowerBoundAbsenceCount() {
        return lowerBoundAbsenceCount;
    }
}
