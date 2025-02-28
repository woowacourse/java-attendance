package domain;

import java.util.ArrayList;
import java.util.List;

public enum Penalty {
    EXPULSION(0, "제적"),
    INTERVIEW(1, "면담"),
    WARNING(2, "경고"),
    NONE(3, "해당 없음"),
    ;

    public static final int PENALTY_THRESHOLD_EXPULSION = 6;
    public static final int PENALTY_THRESHOLD_INTERVIEW = 3;
    public static final int PENALTY_THRESHOLD_WARNING = 2;
    public static final int LATE_TO_ABSENT_UNIT = 3;

    private final int priority;
    private final String message;

    Penalty(int priority, String message) {
        this.message = message;
        this.priority = priority;
    }

    public static String findPenaltyMessageByAttendanceStatusCount(int lateCount, int absentCount) {
        return findPenaltyByAttendanceStatusCount(lateCount, absentCount).message;
    }

    public static Penalty findPenaltyByAttendanceStatusCount(int lateCount, int absentCount) {
        int penaltyPoint = calculatePenaltyPoint(lateCount, absentCount);
        return findPenaltyByPenaltyPoint(penaltyPoint);
    }

    private static int calculatePenaltyPoint(int lateCount, int absentCount) {
        return absentCount + (lateCount / LATE_TO_ABSENT_UNIT);
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

    public static List<Penalty> valuesWithoutNone() {
        List<Penalty> values = new ArrayList<>(List.of(Penalty.values()));
        values.remove(Penalty.NONE);
        return values;
    }

    public String getMessage() {
        return message;
    }

    public int getPriority() {
        return priority;
    }
}
