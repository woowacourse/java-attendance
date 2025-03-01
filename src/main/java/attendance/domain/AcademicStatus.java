package attendance.domain;

public enum AcademicStatus {
    INTERVIEW("면담"),
    WARN("경고"),
    EXPELLED("제적"),
    NOT("대상 외");

    private final String value;

    private static final int LIMIT_EXPELLED = 5;
    private static final int LIMIT_INTERVIEW = 3;
    private static final int LIMIT_WARN = 2;
    private static final int LATE_PER_ABSENCE = 3;

    AcademicStatus(final String value) {

        this.value = value;
    }

    public static AcademicStatus getAcademicStatus(final int late, final int absent) {

        final int count = late / LATE_PER_ABSENCE + absent;
        if (count > LIMIT_EXPELLED) {
            return EXPELLED;
        }
        if (count >= LIMIT_INTERVIEW) {
            return WARN;
        }
        if (count == LIMIT_WARN) {
            return INTERVIEW;
        }
        return NOT;
    }

    public String getValue() {

        return value;
    }
}
