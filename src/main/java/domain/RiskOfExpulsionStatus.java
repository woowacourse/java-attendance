package domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum RiskOfExpulsionStatus {
    NORMAL(0),
    WARNING(2),
    INTERVIEW(3),
    EXPULSION(6);

    private final int absenceCountBoundary;

    RiskOfExpulsionStatus(final int absenceCountBoundary) {
        this.absenceCountBoundary = absenceCountBoundary;
    }

    public static RiskOfExpulsionStatus calculateRiskOfExpulsionStatus(final int absenceCount) {
        return sortDescByAbsenceCountBoundary().stream()
                .filter(status -> absenceCount >= status.absenceCountBoundary)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 결석 횟수입니다."));
    }

    private static List<RiskOfExpulsionStatus> sortDescByAbsenceCountBoundary() {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(RiskOfExpulsionStatus::getAbsenceCountBoundary).reversed())
                .collect(Collectors.toList());
    }

    public int getAbsenceCountBoundary() {
        return absenceCountBoundary;
    }
}
