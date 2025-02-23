package domain.rule;

import domain.AttendanceStatistics;

public enum AbsentRule {
    EXPULSION("제적", 5),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NONE("정상", 0),
    ;

    public static final int LATE_TO_ABSENT_RATIO = 3;

    private final String description;
    private final int absentCount;

    AbsentRule(String description, int absentCount) {
        this.description = description;
        this.absentCount = absentCount;
    }

    public static AbsentRule calculateAbsentPolicy(AttendanceStatistics attendanceStatistics) {
        int absentCount = attendanceStatistics.getAdjustedAbsentCount();

        if (absentCount > EXPULSION.absentCount) {
            return AbsentRule.EXPULSION;
        }

        if (absentCount >= INTERVIEW.absentCount) {
            return AbsentRule.INTERVIEW;
        }

        if (absentCount >= WARNING.absentCount) {
            return AbsentRule.WARNING;
        }

        return AbsentRule.NONE;
    }

    public boolean isRiskOfExpulsion() {
        return this == AbsentRule.WARNING || this == AbsentRule.INTERVIEW;
    }

    public String getDescription() {
        return description;
    }
}
