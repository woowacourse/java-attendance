package domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum PenaltyPolicy {

    EXPULSION(1), MEETING(2), WARNING(3), NONE(4);

    private static final int EXPULSION_STANDARD = 6;
    private static final int MEETING_STANDARD = 3;
    private static final int WARNING_STANDARD = 2;
    private static final int LATE_COUNT_PER_ABSENCE = 3;

    private final int priority;

    PenaltyPolicy(int priority) {
        this.priority = priority;
    }

    private static final List<PenaltyPolicy> dangerTypes = Arrays.asList(WARNING, MEETING, EXPULSION);

    public static PenaltyPolicy judgePenalty(Map<AttendanceType, Integer> counts) {
        int convertedAbsenceCount = getConvertedCount(counts);
        if (convertedAbsenceCount >= EXPULSION_STANDARD) {
            return EXPULSION;
        }
        if (convertedAbsenceCount >= MEETING_STANDARD) {
            return MEETING;
        }
        if (convertedAbsenceCount >= WARNING_STANDARD) {
            return WARNING;
        }
        return NONE;
    }

    public static int getConvertedCount(Map<AttendanceType, Integer> counts) {
        int lateCount = counts.getOrDefault(AttendanceType.LATE, 0);
        return lateCount / LATE_COUNT_PER_ABSENCE + counts.getOrDefault(AttendanceType.ABSENCE, 0);
    }

    public boolean isDanger() {
        return dangerTypes.contains(this);
    }

    public int compareWithPriority(PenaltyPolicy o) {
        return this.priority - o.priority;
    }
}
