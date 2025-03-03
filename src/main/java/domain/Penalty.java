package domain;

import java.util.Arrays;

public enum Penalty {
    EXPULSION(0, "제적", 6),
    INTERVIEW(1, "면담", 3),
    WARNING(2, "경고", 2),
    NONE(3, "해당 없음", 0),
    ;

    public static final int LATE_TO_ABSENT_UNIT = 3;

    private final int priority;
    private final String message;
    private final int threshold;

    Penalty(int priority, String message, int threshold) {
        this.priority = priority;
        this.message = message;
        this.threshold = threshold;
    }

    public static String findPenaltyMessageByAttendanceStatusCount(int lateCount, int absentCount) {
        return findPenaltyByAttendanceStatusCount(lateCount, absentCount).message;
    }

    public static Penalty findPenaltyByAttendanceStatusCount(int lateCount, int absentCount) {
        int penaltyPoint = calculatePenaltyPoint(lateCount, absentCount);
        return findPenaltyByPenaltyPoint(penaltyPoint);
    }

    public static int calculatePenaltyPoint(int lateCount, int absentCount) {
        return absentCount + (lateCount / LATE_TO_ABSENT_UNIT);
    }

    private static Penalty findPenaltyByPenaltyPoint(int penaltyPoint) {
        return Arrays.stream(Penalty.values())
                .filter(penalty -> penaltyPoint >= penalty.threshold)
                .findFirst()
                .orElse(NONE);
    }

    public String getMessage() {
        return message;
    }

    public int getPriority() {
        return priority;
    }

    public int getThreshold() {
        return threshold;
    }
}
