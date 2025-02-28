package domain.policy.absent;

import domain.AttendanceCounts;

public enum AbsentRule {
    EXPULSION("제적", 5),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NONE("정상", 0),
    ;

    public static final int LATE_TO_ABSENT_RATIO = 3;

    private final String description;
    private final int cutoffAbsentCount;

    AbsentRule(String description, int cutoffAbsentCount) {
        this.description = description;
        this.cutoffAbsentCount = cutoffAbsentCount;
    }

    public static AbsentRule calculateAbsentPolicy(AttendanceCounts attendanceCounts) {
        int absentCount = attendanceCounts.getAdjustedAbsentCount();

        if (absentCount > EXPULSION.cutoffAbsentCount) {
            return AbsentRule.EXPULSION;
        }

        if (absentCount >= INTERVIEW.cutoffAbsentCount) {
            return AbsentRule.INTERVIEW;
        }

        if (absentCount >= WARNING.cutoffAbsentCount) {
            return AbsentRule.WARNING;
        }

        return AbsentRule.NONE;
    }

    public boolean isRiskOfExpulsion() {
        return this != NONE;
    }

    public String getDescription() {
        return description;
    }
}
