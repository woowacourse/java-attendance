package model;

public enum StudentPunishment {
    WARNING(2),
    INTERVIEW(3),
    DISMISSAL(5);

    private final int standard;

    StudentPunishment(int standard) {
        this.standard = standard;
    }

    public static StudentPunishment determineDisciplinaryAction(int riskLevel) {
        if (riskLevel >= DISMISSAL.standard) {
            return DISMISSAL;
        }
        if (riskLevel >= INTERVIEW.standard) {
            return INTERVIEW;
        }
        if (riskLevel >= WARNING.standard) {
            return WARNING;
        }
        return null;
    }

    public int getStandard() {
        return standard;
    }

}
