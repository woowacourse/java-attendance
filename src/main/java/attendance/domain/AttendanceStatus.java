package attendance.domain;

public enum AttendanceStatus {
    CHECKIN("출석"),
    ABSENCE("결석"),
    LATE("지각")
    ;

    private String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
