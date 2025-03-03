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

    public static AttendanceWarning calcuateWarning(int countAbsence) {
        return NONE;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
