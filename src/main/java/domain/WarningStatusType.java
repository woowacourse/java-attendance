package domain;

public enum WarningStatusType {

    DISMISSAL("제적"),
    INTERVIEW("면담"),
    WARNING("경고"),
    GOOD("모범");

    private final String type;
    static final int LATE_TO_ABSENT_COUNT = 3;
    static final int DISMISSAL_COUNT = 5;
    static final int INTERVIEW_COUNT = 3;
    static final int WARNING_COUNT = 2;

    WarningStatusType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static WarningStatusType calculateStatus(int late, int absent) {

        int total = absent + late / LATE_TO_ABSENT_COUNT;

        if (total > DISMISSAL_COUNT) {
            return DISMISSAL;
        }
        if (total >= INTERVIEW_COUNT) {
            return INTERVIEW;
        }
        if (total >= WARNING_COUNT) {
            return WARNING;
        }
        return GOOD;
    }
}
