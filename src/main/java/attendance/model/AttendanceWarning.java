package attendance.model;

public enum AttendanceWarning {
    EXPULSION(5),
    COUNSELING(3),
    WARNING(2),
    NONE(0),
    ;

    private final int absenceCount;

    AttendanceWarning(int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
