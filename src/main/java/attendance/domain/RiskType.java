package attendance.domain;

import java.util.Arrays;

public enum RiskType {
    WITHDRAWAL("제적", 6),
    COUNSELING("면담", 3),
    WARNING("경고", 2),
    NONE("해당없음", 0);

    private final String name;
    private final int score;

    RiskType(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public static RiskType find(final int expulsionCount, final int lateCount) {
        int allScore = expulsionCount + (lateCount / 3);
        return Arrays.stream(RiskType.values())
                .filter(type -> type.score <= allScore)
                .findAny()
                .orElse(NONE);
    }

    public String getName() {
        return name;
    }
}
