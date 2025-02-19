package attendance.domain;

public enum AttendanceDismissStatus {
    NEED_MEETING("면담"),
    WARNING("경고"),
    DISMISS("제적"),
    NONE(""),
    ;

    public String getStatus() {
        return status;
    }

    private String status;

    AttendanceDismissStatus(String status) {
        this.status = status;
    }
}
