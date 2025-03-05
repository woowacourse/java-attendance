package domain.attendance;

public enum AttendanceWarning {
    EXPELLED(6),
    INTERVIEW(3),
    WARNING(2),
    NONE(0),
    ;
    private final int absenceCount;

    AttendanceWarning(int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public static AttendanceWarning calculateWarning(int countAbsence) {
        if (EXPELLED.absenceCount <= countAbsence) {
            return EXPELLED;
        }
        if (INTERVIEW.absenceCount <= countAbsence) {
            return INTERVIEW;
        }
        if (WARNING.absenceCount <= countAbsence) {
            return WARNING;
        }
        return NONE;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
