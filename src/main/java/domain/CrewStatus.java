package domain;

public enum CrewStatus {
    NORMAL,
    WARNING,
    INTERVIEW,
    EXPELLED;

    private static final int LATE_TO_UNATTENDED_UNIT = 3;
    private static final int WARNING_COUNT = 2;
    private static final int INTERVIEW_COUNT = 3;
    private static final int EXPELLED_COUNT = 6;

    public static CrewStatus checkCrewStatus(int lateCount, int unattendedCount) {
        int totalUnattendedCount = lateCount / LATE_TO_UNATTENDED_UNIT + unattendedCount;
        if (totalUnattendedCount >= EXPELLED_COUNT) {
            return EXPELLED;
        }
        if (totalUnattendedCount >= INTERVIEW_COUNT) {
            return INTERVIEW;
        }
        if (totalUnattendedCount >= WARNING_COUNT) {
            return WARNING;
        }
        return NORMAL;
    }
}
