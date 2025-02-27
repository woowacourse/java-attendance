package attendance.domain;

import java.util.Arrays;

public enum PenaltyType {
    EXPULSION(6),
    COUNSELING(3),
    WARNING(2);

    private final int penaltyCounts;

    PenaltyType(final int penaltyCounts) {
        this.penaltyCounts = penaltyCounts;
    }

    public static PenaltyType fetchPenaltyType(final int crewPenaltyCounts) {
        return Arrays.stream(PenaltyType.values())
                .filter(count -> count.penaltyCounts <= crewPenaltyCounts)
                .findFirst()
                .orElseThrow();
    }
}
