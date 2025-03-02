package model;

import java.util.Arrays;
import java.util.Comparator;

public enum AttendancePenalty {
    NONE("없음", 0),
    WARNING("경고", 2),
    COUNSELING("면담", 3),
    EXPULSION("제적", 5);

    private final String penalty;
    private final int thresholdAbsenceCount;

    AttendancePenalty(String penalty, int penaltyCount) {
        this.penalty = penalty;
        this.thresholdAbsenceCount = penaltyCount;
    }

    public static AttendancePenalty findPenaltyByAbsentCount(long absentCount) {
         return Arrays.stream(AttendancePenalty.values())
                .sorted(Comparator.comparingInt(AttendancePenalty::getThresholdAbsenceCount).reversed())
                .filter(attendancePenalty -> absentCount > attendancePenalty.getThresholdAbsenceCount())
                .findFirst()
                .orElse(NONE);
    }

    public String getPenalty() {
        return penalty;
    }

    public int getThresholdAbsenceCount() {
        return thresholdAbsenceCount;
    }
}
