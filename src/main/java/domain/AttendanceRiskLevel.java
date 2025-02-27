package domain;

public enum AttendanceRiskLevel {
    EXPULSION("제적"),
    COUNSELING("면담"),
    WARNING("경고"),
    NORMAL("정상"),
    ;

    AttendanceRiskLevel(String status) {}

    public static AttendanceRiskLevel calculateByAbsenceCount(final int count) {
        if (count > 5) {
            return AttendanceRiskLevel.EXPULSION;
        }
        if (count >= 3) {
            return AttendanceRiskLevel.COUNSELING;
        }
        if (count >= 2) {
            return AttendanceRiskLevel.WARNING;
        }
        return AttendanceRiskLevel.NORMAL;
    }
}

