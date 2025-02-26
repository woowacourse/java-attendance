package attendance.domain.risk;

import java.util.List;

public enum RiskType {
    EXPULSION(5),
    COUNSELING(3),
    WARNING(2),
    NONE(0);

    private final int absenceScore;

    RiskType(int absenceScore) {
        this.absenceScore = absenceScore;
    }

    public static RiskType parse(int absenceCount, int lateCount) {
        int currentAbsenceScore = absenceCount + lateCount % 3;
        List<RiskType> types = List.of(RiskType.values());
        return types.stream()
                .filter(type -> currentAbsenceScore >= type.absenceScore)
                .findFirst()
                .orElse(NONE);
    }
}
