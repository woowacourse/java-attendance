package attendance.domain;

import java.util.Arrays;

public enum PenaltyType {
    EXPULSION(6),
    COUNSELING(3),
    WARNING(2),
    NONE(0);

    private final int penaltyCounts;

    PenaltyType(final int penaltyCounts) {
        this.penaltyCounts = penaltyCounts;
    }

    public static PenaltyType fetchPenaltyType(final int crewAbsenceCounts, final int crewLateCounts) {
        return Arrays.stream(PenaltyType.values())
                .filter(count -> count.penaltyCounts <= calculateTotalPenaltyCounts(crewAbsenceCounts, crewLateCounts))
                .findFirst()
                .orElseThrow();
    }

    private static int calculateTotalPenaltyCounts(final int crewAbsenceCounts, final int crewLateCounts) {
        return crewAbsenceCounts + (crewLateCounts / 3);
    }

}
