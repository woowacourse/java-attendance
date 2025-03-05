package domain;

public record RiskStatusResult(
        Crew crew,
        int attendanceCount,
        int tardyCount,
        int absenceCount,
        RiskStatus riskStatus
) {
    public RiskStatusResult(Crew crew, int attendanceCount, int tardyCount, int absenceCount) {
        this(crew, attendanceCount, tardyCount, absenceCount, RiskStatus.evaluateStatus(tardyCount, absenceCount));
    }
}
