package domain;

public enum Penalty {
    LEAVE("제적"),
    INTERVIEW("면담"),
    WARNING("경고"),
    NONE("");

    private final String description;

    Penalty(String description) {
        this.description = description;
    }

    public static Penalty determine(int late, int absence) {
        int totalAbsence = absence + (late / 3);
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
