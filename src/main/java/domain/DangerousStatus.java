package domain;

import static domain.AttendTime.LATE_TO_ABSENT_COUNT;

public class DangerousStatus {

    public static final String DISMISSAL = "제적";
    public static final String INTERVIEW = "면담";
    public static final String WARNING = "경고";
    public static final String GOOD = "모범";
    public static final int DISMISSAL_COUNT = 5;
    public static final int INTERVIEW_COUNT = 3;
    public static final int WARNING_COUNT = 2;

    private int onTime;
    private int late;
    private int absent;
    private String status;

    public DangerousStatus(int onTime, int late, int absent) {
        this.onTime = onTime;
        this.late = late;
        this.absent = absent;
    }

    public void calculateStatus() {
        int total = absent;
        total += late / LATE_TO_ABSENT_COUNT;

        if (total > DISMISSAL_COUNT) {
            status = DISMISSAL;
            return;
        }
        if (total >= INTERVIEW_COUNT) {
            status = INTERVIEW;
            return;
        }
        if (total >= WARNING_COUNT) {
            status = WARNING;
            return;
        }
        status = GOOD;
    }

    public String getStatus() {
        calculateStatus();
        return status;
    }
}
