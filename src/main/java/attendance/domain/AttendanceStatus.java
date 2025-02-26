package attendance.domain;

public enum AttendanceStatus {
    ATTENDANCE("출석", 31),
    LATE("지각", 6),
    ABSENCE("결석", 0);

    private final String status;
    private final int minutes;

    AttendanceStatus(String status, int minutes) {
        this.status = status;
        this.minutes = minutes;
    }

    public String getStatus() {
        return status;
    }

    public int getMinutes() {
        return minutes;
    }
}
