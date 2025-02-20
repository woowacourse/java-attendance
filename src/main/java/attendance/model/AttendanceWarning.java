package attendance.model;

public enum AttendanceWarning {
    제적(6),
    면담(3),
    경고(2),
    해당없음(0),
    ;

    private final int absenceCount;

    AttendanceWarning(int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public static AttendanceWarning from(Crew crew) {
        long absenceCount = calculateAbsenceCount(
                crew.getAttendanceHistory().getAbsenceCount(),
                crew.getAttendanceHistory().getLateCount()
        );
        if (absenceCount > 제적.absenceCount) {
            return 제적;
        }
        if (absenceCount >= 면담.absenceCount) {
            return 면담;
        }
        if (absenceCount >= 경고.absenceCount) {
            return 경고;
        }
        return 해당없음;
    }

    public static long calculateAbsenceCount(long absenceCount, long lateCount) {
        return absenceCount + lateCount / 3;
    }
}
