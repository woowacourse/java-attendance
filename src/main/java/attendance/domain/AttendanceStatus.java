package attendance.domain;

public enum AttendanceStatus {
    PRESENT("출석"), LATENESS("지각"), ABSENCE("결석");

    private final String displayName;
    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
