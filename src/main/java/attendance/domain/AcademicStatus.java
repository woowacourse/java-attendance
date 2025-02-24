package attendance.domain;

public enum AcademicStatus {

    WARNING("경고"),
    INTERVIEW("면담"),
    EXPELLED("제적"),
    NOT("X");

    private final String value;

    private static final int EXPELLED_COUNT = 5;
    private static final int INTERVIEW_COUNT = 3;
    private static final int WARNING_COUNT = 2;
    private static final int LATE_AS_ABSENT = 3;

    AcademicStatus(String value) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AcademicStatus getAcademicStatus(int late, int absent) {

        int count = late / LATE_AS_ABSENT + absent;
        if (count > EXPELLED_COUNT) {
            return EXPELLED;
        }
        if (count >= INTERVIEW_COUNT) {
            return INTERVIEW;
        }
        if (count == WARNING_COUNT) {
            return WARNING;
        }
        return NOT;
    }
}
