package domain;

public class AttendanceStatus {
    int onTime;
    int late;
    int absent;
    String status;

    public AttendanceStatus(int onTime, int late, int absent) {
        this.onTime = onTime;
        this.late = late;
        this.absent = absent;
    }

    public void calculateStatus() {
        int total = absent;
        total += late / 3;

        if (total > 5) {
            status = "제적";
            return;
        }
        if (total >= 3) {
            status = "면담";
            return;
        }
        if (total >= 2) {
            status = "경고";
        }
    }

    public String getStatus() {
        calculateStatus();
        return status;
    }
}
