package domain;

import java.util.Arrays;

public enum PenaltyType {
    EXPULSION("제적", 6),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NONE("", 0);

    private String value;
    private int minCount;

    PenaltyType(String value, int minCount) {
        this.value = value;
        this.minCount = minCount;
    }

    public static PenaltyType getPenaltyType(int count) {
        return Arrays.stream(PenaltyType.values())
                .filter(p -> count >= p.minCount)
                .findFirst()
                .orElse(NONE);
    }
}
