package attendance.domain;

public enum SanctionLevel {
    NEED_MEETING("면담"),
    WARNING("경고"),
    DISMISS("제적"),
    NONE(""),
    ;

    public String getStatus() {
        return status;
    }

    private final String status;

    SanctionLevel(String status) {
        this.status = status;
    }
}
