package domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum PenaltyPolicy {

    EXPULSION, MEETING, WARNING, NONE;

    private static final int EXPULSION_STANDARD = 6;
    private static final int MEETING_STANDARD = 3;
    private static final int WARNING_STANDARD = 2;
    private static final int LATE_COUNT_PER_ABSENCE = 3;

    private static final List<PenaltyPolicy> dangerTypes = Arrays.asList(WARNING, MEETING, EXPULSION);

    public static PenaltyPolicy judgePenalty(Map<AttendanceType, Integer> counts) {

        int lateCount = counts.getOrDefault(AttendanceType.LATE, 0);
        int convertedAbsenceCount = lateCount / LATE_COUNT_PER_ABSENCE + counts.getOrDefault(AttendanceType.ABSENCE, 0);
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

    public boolean isDanger() {
        return dangerTypes.contains(this);
    }
}
