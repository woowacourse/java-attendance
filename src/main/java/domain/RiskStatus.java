package domain;

import java.util.Arrays;
import java.util.Comparator;

public enum RiskStatus {
    EXPULSION(6),
    COUNSELING(3), WARNING(2), NONE(0);

    private static final int TARDY_TO_ABSENCE_RATIO = 3;
    private final int riskValue;

    RiskStatus(int riskValue) {
        this.riskValue = riskValue;
    }

    public static RiskStatus getRiskStatus(int absenceCount, int tardyCount) {
        int riskValue = calculateRiskValue(absenceCount, tardyCount);
        return Arrays.stream(RiskStatus.values())
                .sorted(Comparator
                        .comparingInt(RiskStatus::getRiskValue)
                        .reversed())
                .filter(riskStatus -> riskValue >= riskStatus.riskValue)
                .findFirst()
                .orElse(NONE);

    }

    public static int calculateRiskValue(int absenceCount, int tardyCount) {
        return absenceCount + tardyCount / TARDY_TO_ABSENCE_RATIO;
    }

    public int getRiskValue() {
        return riskValue;
    }
}
