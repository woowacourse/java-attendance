package domain;

import static domain.AttendTime.LATE_TO_ABSENT_COUNT;
import static domain.Dangerous.DISMISSAL;
import static domain.Dangerous.GOOD;
import static domain.Dangerous.INTERVIEW;
import static domain.Dangerous.WARNING;

public class DangerousStatus {

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

        if (total > DISMISSAL.getCount()) {
            status = DISMISSAL.getStatus();
            return;
        }
        if (total >= INTERVIEW.getCount()) {
            status = INTERVIEW.getStatus();
            return;
        }
        if (total >= WARNING.getCount()) {
            status = WARNING.getStatus();
            return;
        }
        status = GOOD.getStatus();
    }

    public String getStatus() {
        calculateStatus();
        return status;
    }
}
