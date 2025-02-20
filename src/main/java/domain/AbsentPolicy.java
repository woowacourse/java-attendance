package domain;

public enum AbsentPolicy {
    EXPULSION("제적", 5),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),

    NONE("정상", 0),
    ;

    public static final int LATE_TO_ABSENT_RATIO = 3;

    public final String description;
    public final int absentCount;

    AbsentPolicy(String description, int absentCount) {
        this.description = description;
        this.absentCount = absentCount;
    }

    public static boolean isRiskOfExpulsion(AbsentPolicy absentPolicy) {
        return absentPolicy == AbsentPolicy.NONE || absentPolicy == AbsentPolicy.EXPULSION;
    }

    public static AbsentPolicy calculateAbsentPolicy(int absentCount, int lateCount) {
        absentCount += lateCount / LATE_TO_ABSENT_RATIO;

        if (absentCount > EXPULSION.absentCount) {
            return AbsentPolicy.EXPULSION;
        }
        if (absentCount >= INTERVIEW.absentCount) {
            return AbsentPolicy.INTERVIEW;
        }
        if (absentCount == WARNING.absentCount) {
            return AbsentPolicy.WARNING;
        }
        return AbsentPolicy.NONE;
    }
}
