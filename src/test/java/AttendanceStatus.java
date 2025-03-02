public enum AttendanceStatus {

    ATTEND("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
