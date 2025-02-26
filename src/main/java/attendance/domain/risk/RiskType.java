package attendance.domain.risk;

import java.util.List;

public enum RiskType {
    EXPULSION(5, "제적"),
    COUNSELING(3, "면담"),
    WARNING(2, "경고"),
    NONE(0, "");

    private final int absenceScore;
    private final String name;

    RiskType(int absenceScore, String name) {
        this.absenceScore = absenceScore;
        this.name = name;
    }

    public static RiskType parse(int absenceCount, int lateCount) {
        int currentAbsenceScore = absenceCount + lateCount / 3;
        List<RiskType> types = List.of(RiskType.values());
        return types.stream()
                .filter(type -> currentAbsenceScore >= type.absenceScore)
                .findFirst()
                .orElse(NONE);
    }

    public String getName() {
        return name;
    }
}
