package domain;

public record RiskStatusResult(
        String name,
        int attendanceCount,
        int tardyCount,
        int absenceCount,
        RiskStatus riskStatus
) {
    public RiskStatusResult(String name, int attendanceCount, int tardyCount, int absenceCount) {
        this(name, attendanceCount, tardyCount, absenceCount, RiskStatus.evaluateStatus(tardyCount, absenceCount));
    }
}
