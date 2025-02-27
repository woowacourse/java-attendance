package domain;

public enum PenaltyStatus {
    NONE("비대상자"),
    CAUTION("경고"),
    INTERVIEW("면담"),
    EXPULSION("제적");

    private static final int LATE_COUNT_FOR_ABSENT_COUNT = 3;
    private static final int CAUTION_LOWER_LIMIT = 2;
    private static final int INTERVIEW_LOWER_LIMIT = 3;
    private static final int EXPULSION_LOWER_LIMIT = 5;


    private final String name;

    PenaltyStatus(String name) {
        this.name = name;
    }

    public static PenaltyStatus findStatusByNickname(String nickname, Attendances attendances) {
        int lateCount = attendances.calculateLateCount(nickname);
        int absentCount = attendances.calculateAbsentCount(nickname);

        if (getTotalAbsentCount(absentCount, lateCount) >= EXPULSION_LOWER_LIMIT) return PenaltyStatus.EXPULSION;

        if (getTotalAbsentCount(absentCount, lateCount) > INTERVIEW_LOWER_LIMIT) return PenaltyStatus.INTERVIEW;
        
        if (getTotalAbsentCount(absentCount, lateCount) > CAUTION_LOWER_LIMIT) return PenaltyStatus.CAUTION;

        return PenaltyStatus.NONE;
    }

    private static int getTotalAbsentCount(int absentCount, int lateCount) {
        return absentCount + lateCount / LATE_COUNT_FOR_ABSENT_COUNT;
    }

    public String getName() {
        return name;
    }
}
