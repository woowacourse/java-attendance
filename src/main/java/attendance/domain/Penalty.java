package attendance.domain;

public enum Penalty {

    REMOVAL("제적", 5),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NONE(null, 0)
    ;

    private static final int LATE_PER_ABSENT = 3;

    private String name;
    private int boundary;

    Penalty(String name, int boundary) {
        this.name = name;
        this.boundary = boundary;
    }

    public static int calculateTotalAbsent(int late, int absent) {
        return absent + (late / LATE_PER_ABSENT);
    }

    public static Penalty determine(int late, int absent) {
        int totalAbsent = calculateTotalAbsent(late, absent);

        if (totalAbsent == WARNING.boundary) {
            return WARNING;
        }

        if (totalAbsent >= INTERVIEW.boundary && totalAbsent <= REMOVAL.boundary) {
            return INTERVIEW;
        }

        if (totalAbsent > REMOVAL.boundary) {
            return REMOVAL;
        }

        return NONE;
    }
}
