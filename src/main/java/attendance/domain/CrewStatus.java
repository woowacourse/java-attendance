package attendance.domain;

public enum CrewStatus {
    EXPEL("제적"),
    MEETING("면담"),
    WARNING("경고");

    private final String status;

    CrewStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
