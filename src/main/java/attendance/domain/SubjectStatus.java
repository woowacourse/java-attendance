package attendance.domain;

public enum SubjectStatus {
    DISMISSED("제적"),
    COUNSELING("면담"),
    WARNING("경고");

    private final String status;

    SubjectStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
