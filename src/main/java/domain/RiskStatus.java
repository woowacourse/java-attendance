package domain;

public enum RiskStatus {
    WARNING,
    COUNSELING,
    EXPULSION,
    NONE;

    public static RiskStatus getRiskStatus(int absenceCount, int tardyCount) {
        int totalAbsenceCount = absenceCount + tardyCount / 3;

        if (totalAbsenceCount > 5) {
            return RiskStatus.EXPULSION;
        }

        if (totalAbsenceCount >= 3) {
            return RiskStatus.COUNSELING;
        }

        if (totalAbsenceCount == 2) {
            return RiskStatus.WARNING;
        }

        return RiskStatus.NONE;
    }

    public boolean hasRisk() {
        return this != RiskStatus.NONE;
    }
}
