package domain.policy;

public enum AttendancePolicy {
    ATTEND_STATUS("출석"),
    LATE_STATUS("지각"),
    ABSENT_STATUS("결석");

    private final String attendanceStatus;

    AttendancePolicy(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getStatus() {
        return attendanceStatus;
    }
}