package attendance.domain;

import java.util.Map;

public class PenaltyCount {

    public static final int LATE_TO_ABSENCE_RATE = 3;

    private final int lateCount;
    private final int absenceCount;

    public PenaltyCount(Map<AttendanceStatus, Integer> statusCounts) {
        this.lateCount = statusCounts.get(AttendanceStatus.LATE);
        this.absenceCount = statusCounts.get(AttendanceStatus.ABSENCE);
    }

    public int calculateWeightedLateAndAbsenceCount() {
        return absenceCount + lateCount / LATE_TO_ABSENCE_RATE;
    }
}
