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
    private final int absentLimit;

    RiskRank(String name, int absentLimit) {
        this.name = name;
        this.absentLimit = absentLimit;
    }

    public static RiskRank from(int absentCount) {
        if (absentCount < 0) {
            throw new IllegalArgumentException(absentCount + ": 결석 횟수는 음수일 수 없습니다.");
        }
        
        return Arrays.stream(values())
                .filter(riskRank -> riskRank.absentLimit <= absentCount)
                .max(Comparator.comparing(RiskRank::getAbsentLimit))
                .orElseThrow(() -> new IllegalStateException("논리적으로 도달할 수 없는 예외입니다."));
    }

    public static String getRiskRankNameByAbsentCount(int absentCount) {
        return RiskRank.from(absentCount).name;
    }

    public String getName() {
        return name;
    }

    public int getAbsentLimit() {
        return absentLimit;
    }
}
