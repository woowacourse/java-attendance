package domain;

public class AttendanceStatus {

    private final int onTime;
    private final int late;
    private final int absent;
    private WarningStatusType status;

    public AttendanceStatus(int onTime, int late, int absent) {
        this.onTime = onTime;
        this.late = late;
        this.absent = absent;
    }

    public WarningStatusType getStatus() {
        status = WarningStatusType.calculateStatus(late, absent);
        return status;
    }
}
