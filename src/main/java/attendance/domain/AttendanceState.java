package attendance.domain;

public enum AttendanceState {
    TARDY;

    public static AttendanceState evaluate(final int overTime) {
        return TARDY;
    }
}
