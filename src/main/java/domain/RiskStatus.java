package domain;

public enum RiskStatus {
    EXPULSION,
    COUNSELING;

    public static RiskStatus getRiskStatus(int absenceCount, int tardyCount) {
        int riskValue = absenceCount + tardyCount / 3;
        if(riskValue > 5) {
            return EXPULSION;
        }
        return COUNSELING;
    }
}
