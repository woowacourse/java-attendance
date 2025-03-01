package domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum RiskRank {

    NOT_MANAGED("", (count) -> count < 2),
    WARNING("경고", (count) -> count == 2),
    INTERVIEW("면담", (count) -> count >= 3 && count <= 5),
    EXPELLED("제적", (count) -> count > 5),
    ;

    private static final int ABSENT_PER_LATE = 3;

    private final String description;
    private final Function<Integer, Boolean> condition;

    RiskRank(String description, Function<Integer, Boolean> condition) {
        this.description = description;
        this.condition = condition;
    }

    public static int calculateRiskCount(int lateCount, int absentCount) {
        return lateCount / ABSENT_PER_LATE + absentCount;
    }

    public static RiskRank from(Map<AttendanceStatus, Integer> statusCount) {
        int riskCount = calculateRiskCount(
                statusCount.getOrDefault(AttendanceStatus.LATE, 0),
                statusCount.getOrDefault(AttendanceStatus.ABSENT, 0));
        if (riskCount < 0) {
            throw new IllegalArgumentException(riskCount + ": 결석 횟수는 음수일 수 없습니다.");
        }

        return Arrays.stream(values())
                .filter(riskRank -> riskRank.condition.apply(riskCount))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("논리적으로 도달할 수 없는 예외입니다."));
    }

    public static RiskRank of(int lateCount, int absentCount) {
        int riskCount = calculateRiskCount(lateCount, absentCount);
        if (riskCount < 0) {
            throw new IllegalArgumentException(riskCount + ": 결석 횟수는 음수일 수 없습니다.");
        }

        return Arrays.stream(values())
                .filter(riskRank -> riskRank.condition.apply(riskCount))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("논리적으로 도달할 수 없는 예외입니다."));
    }

    public String getDescription() {
        return description;
    }
}
