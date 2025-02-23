package domain;

public enum RiskStatus {
    WARNING(2),
    COUNSELING(3),
    EXPULSION(5),
    NONE(0);

    private final int absenceThresholdCount;

    RiskStatus(final int absenceThresholdCount) {
        this.absenceThresholdCount = absenceThresholdCount;
    }

    public static RiskStatus getRiskStatus(int absenceCount, int tardyCount) {
        int totalAbsenceCount = calculateTotalAbsenceCount(absenceCount, tardyCount);

        if (totalAbsenceCount > EXPULSION.absenceThresholdCount) {
            return RiskStatus.EXPULSION;
        }

        if (totalAbsenceCount >= COUNSELING.absenceThresholdCount) {
            return RiskStatus.COUNSELING;
        }

        if (totalAbsenceCount == WARNING.absenceThresholdCount) {
            return RiskStatus.WARNING;
        }

        return RiskStatus.NONE;
    }

    public static int calculateTotalAbsenceCount(int absenceCount, int tardyCount) {
        return absenceCount + tardyCount / 3;
    }

    public boolean hasRisk() {
        return this != RiskStatus.NONE;
    }
}
