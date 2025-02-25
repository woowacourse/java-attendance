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
        int riskValue = absenceCount + tardyCount / TARDY_TO_ABSENCE_RATIO;
        return Arrays.stream(RiskStatus.values())
                .sorted(Comparator
                        .comparingInt(RiskStatus::getRiskValue)
                        .reversed())
                .filter(riskStatus -> riskValue >= riskStatus.riskValue)
                .findFirst()
                .orElse(NONE);

    }

    private int getRiskValue() {
        return riskValue;
    }
}
