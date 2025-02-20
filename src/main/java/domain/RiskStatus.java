package domain;

public enum RiskStatus {
    WARNING("경고"),
    COUNSELING("면담"),
    EXPULSION("제적"),
    NONE("-");

    private final String status;

    RiskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static RiskStatus getRiskStatus(int absenceCount, int tardyCount) {
        int totalAbsenceCount = absenceCount + tardyCount / 3;

        if (totalAbsenceCount > 5) {
            return RiskStatus.EXPULSION;
        }

        if (totalAbsenceCount >= 3) {
            return RiskStatus.COUNSELING;
        }

        if (totalAbsenceCount >= 2) {
            return RiskStatus.WARNING;
        }

        return RiskStatus.NONE;
    }
}
