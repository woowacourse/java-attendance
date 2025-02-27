package domain;

import java.util.Arrays;
import java.util.Comparator;

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
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(RiskOfExpulsionStatus::getAbsenceCountBoundary).reversed())
                .filter(status -> absenceCount >= status.absenceCountBoundary)
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

    public int getAbsenceCountBoundary() {
        return absenceCountBoundary;
    }
}
