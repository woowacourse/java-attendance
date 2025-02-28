package domain;

import dto.AttendanceCount;

public enum Penalty {
    EXPULSION("제적"),
    COUNSELING("면담"),
    WARNING("경고"),
    NONE("X");

    private final String expression;
    private static final int EXPULSION_CONDITION = 6;
    private static final int COUNSELING_CONDITION = 3;
    private static final int WARNING_CONDITION = 2;

    Penalty(String expression) {
        this.expression = expression;
    }

    public static Penalty from(AttendanceCount attendanceCount) {
        if (attendanceCount.consideredAbsentCount() >= EXPULSION_CONDITION) {
            return EXPULSION;
        }
        if (attendanceCount.consideredAbsentCount() >= COUNSELING_CONDITION) {
            return COUNSELING;
        }
        if (attendanceCount.consideredAbsentCount() == WARNING_CONDITION) {
            return WARNING;
        }
        return NONE;
    }

    public String getExpression() {
        return expression;
    }
}
