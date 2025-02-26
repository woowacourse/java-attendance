package domain;

import java.util.Arrays;
import java.util.Comparator;

public enum RiskRank {
    NOT_MANAGED("", 0),
    WARNING("경고", 2),
    INTERVIEW("면담", 3),
    EXPELLED("제적", 6),
    ;

    private final String name;
    private final int riskCountLimit;

    RiskRank(String name, int riskCountLimit) {
        this.name = name;
        this.riskCountLimit = riskCountLimit;
    }

    public static RiskRank from(int riskCount) {
        if (riskCount < 0) {
            throw new IllegalArgumentException(riskCount + ": 결석 횟수는 음수일 수 없습니다.");
        }

        return Arrays.stream(values())
                .filter(riskRank -> riskRank.riskCountLimit <= riskCount)
                .max(Comparator.comparing(RiskRank::getRiskCountLimit))
                .orElseThrow(() -> new IllegalStateException("논리적으로 도달할 수 없는 예외입니다."));
    }

    public String getName() {
        return name;
    }

    public int getRiskCountLimit() {
        return riskCountLimit;
    }
}
