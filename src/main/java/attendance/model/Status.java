package attendance.model;

public enum Status {
    NONE(""),
    WARNING("경고"),
    MEETING("면담"),
    EXPEL("제적");

    private final String status;

    Status(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
