package domain;

public enum Penalty {
    LEAVE("제적"),
    INTERVIEW("면담"),
    WARNING("경고"),
    NONE("");

    public static final int LATE_TO_ABSENCE_RATE = 3;

    private final String description;

    Penalty(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public static Penalty determine(int late, int absence) {
        int totalAbsence = absence + (late / LATE_TO_ABSENCE_RATE);
        if (totalAbsence > 5) {
            return LEAVE;
        }
        if (totalAbsence >= 3) {
            return INTERVIEW;
        }
        if (totalAbsence >= 2) {
            return WARNING;
        }
        return NONE;
    }
}
