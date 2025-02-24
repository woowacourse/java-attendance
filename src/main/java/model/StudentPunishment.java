package model;

public enum StudentPunishment {
    WARNING(2),
    INTERVIEW(3),
    DISMISSAL(5);

    private final int absenceLimit;

    StudentPunishment(int absenceLimit) {
        this.absenceLimit = absenceLimit;
    }

    public int getAbsenceLimit() {
        return absenceLimit;
    }
}
