package domain;

public class AttendanceStatus {

    int onTime;
    int late;
    int absent;
    WarningStatusType status;

    public AttendanceStatus(int onTime, int late, int absent) {
        this.onTime = onTime;
        this.late = late;
        this.absent = absent;
    }

    public WarningStatusType getStatus() {
        status = WarningStatusType.calculateStatus(onTime, late, absent);
        return status;
    }
}
