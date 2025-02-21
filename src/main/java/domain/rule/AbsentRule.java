package domain.rule;

public enum AbsentRule {
    EXPULSION("제적", 5),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NONE("정상", 0),
    ;

    public static final int LATE_TO_ABSENT_RATIO = 3;

    public final String description;
    public final int absentCount;

    AbsentRule(String description, int absentCount) {
        this.description = description;
        this.absentCount = absentCount;
    }

    public static boolean isRiskOfExpulsion(AbsentRule absentRule) {
        return absentRule == AbsentRule.NONE || absentRule == AbsentRule.EXPULSION;
    }

    public static AbsentRule calculateAbsentPolicy(int absentCount, int lateCount) {
        absentCount += lateCount / LATE_TO_ABSENT_RATIO;

        if (absentCount > EXPULSION.absentCount) {
            return AbsentRule.EXPULSION;
        }

        if (absentCount >= INTERVIEW.absentCount) {
            return AbsentRule.INTERVIEW;
        }

        if (absentCount == WARNING.absentCount) {
            return AbsentRule.WARNING;
        }

        return AbsentRule.NONE;
    }
}
