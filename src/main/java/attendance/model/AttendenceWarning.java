package attendance.model;

public enum AttendenceWarning {

    경고(2),
    면담(3),
    제적(6),
    해당없음(0),
    ;

    private final int absenceCount;

    AttendenceWarning(int absenceCount) {
        this.absenceCount = absenceCount;
    }


    public static AttendenceWarning from(int absenceCount) {
        if (absenceCount > 5) {
            return 제적;
        }
        if (absenceCount >= 3) {
            return 면담;
        }
        if (absenceCount >= 2) {
            return 경고;
        }
        return 해당없음;
    }
}
