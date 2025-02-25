package attendance.domain;

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
        return NONE;
    }
}
