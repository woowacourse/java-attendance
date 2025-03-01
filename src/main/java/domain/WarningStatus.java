package domain;

public enum WarningStatus {
    PASS,
    WARNING,
    INTERVIEW,
    EXPEL;

    private static final long LATE_ABSENCE_RATIO = 3;

    public static long calculateTotalAbsence(final AttendCount attendCount) {
        return attendCount.lateCount() / LATE_ABSENCE_RATIO + attendCount.absenceCount();
    }
}
