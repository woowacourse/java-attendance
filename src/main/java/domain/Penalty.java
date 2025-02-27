package domain;

public enum Penalty {
    WARNING("경고"),
    INTERVIEW("면담"),
    EXPULSION("제적"),
    NONE("해당 없음");

    public static final int PENALTY_THRESHOLD_EXPULSION = 6;
    public static final int PENALTY_THRESHOLD_INTERVIEW = 3;
    public static final int PENALTY_THRESHOLD_WARNING = 2;

    private final String message;

    Penalty(String message) {
        this.message = message;
    }

    public static String findPenaltyMessageByAttendanceStatusCount(int lateCount, int absentCount) {
        int penaltyPoint = calculatePenaltyPoint(lateCount, absentCount);
        return findPenaltyByPenaltyPoint(penaltyPoint).message;
    }

    private static int calculatePenaltyPoint(int lateCount, int absentCount) {
        return absentCount + (lateCount / 3);
    }

    private static Penalty findPenaltyByPenaltyPoint(int penaltyPoint) {
        if (penaltyPoint >= PENALTY_THRESHOLD_EXPULSION) {
            return EXPULSION;
        }
        if (penaltyPoint >= PENALTY_THRESHOLD_INTERVIEW) {
            return INTERVIEW;
        }
        if (penaltyPoint >= PENALTY_THRESHOLD_WARNING) {
            return WARNING;
        }
        return NONE;
    }

    public String getMessage() {
        return message;
    }
}
