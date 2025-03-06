package domain;

public enum RiskStatus {
    DISMISSAL,
    COUNSELLING,
    WARNING,
    NONE;

    private final static int DISMISSAL_COUNT = 5;
    private final static int COUNSELLING_COUNT = 3;
    private final static int WARNING_COUNT = 2;

    public static RiskStatus evaluateStatus(int tardyCount, int absenceCount) {
        final int totalCount = tardyCount / 3 + absenceCount;

        if (totalCount > DISMISSAL_COUNT) {
            return DISMISSAL;
        }

        if (totalCount >= COUNSELLING_COUNT) {
            return COUNSELLING;
        }

        if (totalCount == WARNING_COUNT) {
            return WARNING;
        }

        return NONE;
    }
}
