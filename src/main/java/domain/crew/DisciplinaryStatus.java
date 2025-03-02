package domain.crew;

public enum DisciplinaryStatus {
    DISMISSED,
    COUNSELING,
    WARNED,
    NORMAL,
    ;

    public static DisciplinaryStatus from(int absenceCount, int lateCount) {
        absenceCount += (lateCount / 3);
        if (absenceCount < 2) {
            return NORMAL;
        }
        if (absenceCount < 3) {
            return WARNED;
        }
        if (absenceCount <= 5) {
            return COUNSELING;
        }
        return DISMISSED;
    }
}
