package attendance.domain;

import java.util.Map;

public class PenaltyCount {

    public static final int LATE_TO_ABSENCE_RATE = 3;

    private final int lateCount;
    private final int absenceCount;
    private final int weightedLateAndAbsencePoint;

    public PenaltyCount(Map<AttendanceStatus, Integer> statusCounts) {
        this.lateCount = statusCounts.get(AttendanceStatus.LATE);
        this.absenceCount = statusCounts.get(AttendanceStatus.ABSENCE);
        this.weightedLateAndAbsencePoint = calculateWeightedLateAndAbsenceCount();
    }

    private int calculateWeightedLateAndAbsenceCount() {
        return absenceCount + lateCount / LATE_TO_ABSENCE_RATE;
    }

    public AttendancePenalty findAttendancePenalty() {
        return AttendancePenalty.findPenalty(weightedLateAndAbsencePoint);
    }

    public int getWeightedLateAndAbsencePoint() {
        return weightedLateAndAbsencePoint;
    }
}
