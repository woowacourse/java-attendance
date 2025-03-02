package domain;

public enum AttendanceRiskLevel {
    EXPULSION("제적"),
    COUNSELING("면담"),
    WARNING("경고"),
    NORMAL("정상"),
    ;

    private final String status;

    AttendanceRiskLevel(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

