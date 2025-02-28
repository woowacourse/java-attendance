package domain;

import java.util.Arrays;
import java.util.function.Function;

public enum RiskRank {
    NOT_MANAGED("", (count) -> count < 2),
    WARNING("경고", (count) -> count == 2),
    INTERVIEW("면담", (count) -> count >= 3 && count <= 5),
    EXPELLED("제적", (count) -> count > 5),
    ;

    private final String name;
    private Function<Integer, Boolean> condition;

    RiskRank(String name, Function<Integer, Boolean> condition) {
        this.name = name;
        this.condition = condition;
    }

    public static RiskRank from(int riskCount) {
        if (riskCount < 0) {
            throw new IllegalArgumentException(riskCount + ": 결석 횟수는 음수일 수 없습니다.");
        }

        return Arrays.stream(values())
                .filter(riskRank -> riskRank.condition.apply(riskCount))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("논리적으로 도달할 수 없는 예외입니다."));
    }
}
