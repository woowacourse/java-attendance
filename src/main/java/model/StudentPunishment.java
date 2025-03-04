package model;

public enum StudentPunishment {
    DISMISSAl(5),
    INTERVIEW(3),
    WARNING(2),
    SAFE(1);

    private final int absenceCount;

    StudentPunishment(int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public static StudentPunishment calculatePunishment(int riskLevel) {
        if (riskLevel >= DISMISSAl.absenceCount) {
            return StudentPunishment.DISMISSAl;
        }
        if (riskLevel >= INTERVIEW.absenceCount) {
            return StudentPunishment.INTERVIEW;
        }
        if (riskLevel >= WARNING.absenceCount) {
            return StudentPunishment.WARNING;
        }
        return StudentPunishment.SAFE;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
