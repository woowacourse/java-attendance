package domain;

public enum DangerousStatus {

    DISMISSAL("제적", 5),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    GOOD("모범", 0);

    private static final int LATE_TO_ABSENCE_RATE = 3;

    private final String status;
    private final int count;

    DangerousStatus(String status, int count) {
        this.status = status;
        this.count = count;
    }

    public static DangerousStatus of(int late, int absence) {
        int total = absence;
        if (late >= LATE_TO_ABSENCE_RATE) {
            total += late / LATE_TO_ABSENCE_RATE;
        }

        if (total > DISMISSAL.count) {
            return DISMISSAL;
        }
        if (total >= INTERVIEW.count) {
            return INTERVIEW;
        }
        if (total >= WARNING.count) {
            return WARNING;
        }
        return GOOD;
    }
}
