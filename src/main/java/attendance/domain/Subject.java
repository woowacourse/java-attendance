package attendance.domain;

public enum Subject {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String status;

    Subject(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
