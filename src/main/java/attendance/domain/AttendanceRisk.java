package attendance.domain;

public enum AttendanceRisk {
    WARNING;

    public static AttendanceRisk evaluate(final int absence) {
        return WARNING;
    }
}
