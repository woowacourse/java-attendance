package domain;

public enum Penalty {

    EXPULSION("제적"),
    COUNSEL("면담"),
    WARNING("경고"),
    PASS("통과");

    private final String description;

    Penalty(String description) {
        this.description = description;
    }

    public static Penalty from(int latenessCount, int absenceCount) {
        absenceCount += latenessCount / 3;
        if (absenceCount > 5) {
            return EXPULSION;
        }
        if (absenceCount >= 3) {
            return COUNSEL;
        }
        if (absenceCount >= 2) {
            return WARNING;
        }
        return PASS;
    }

    public boolean isPenaltyCrew() {
        return this != PASS;
    }

    public String getDescription() {
        return description;
    }
}
