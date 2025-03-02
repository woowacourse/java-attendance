package attendance.domain;

import java.util.Map;
import java.util.Objects;

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

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getWeightedLateAndAbsencePoint() {
        return weightedLateAndAbsencePoint;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PenaltyCount that = (PenaltyCount) o;
        return lateCount == that.lateCount && absenceCount == that.absenceCount && weightedLateAndAbsencePoint == that.weightedLateAndAbsencePoint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lateCount, absenceCount, weightedLateAndAbsencePoint);
    }
}
