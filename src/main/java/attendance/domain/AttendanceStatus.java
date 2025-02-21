package attendance.domain;

public enum AttendanceStatus {
    PRESENT("출석"), LATENESS("지각"), ABSENCE("결석");

    private final String status;
    AttendanceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
