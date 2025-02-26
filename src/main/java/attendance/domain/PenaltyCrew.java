package attendance.domain;

import java.util.Map;

public class PenaltyCrew {

    private final String name;
    private final PenaltyCount penaltyCount;
    private final AttendancePenalty penalty;

    public PenaltyCrew(String name, Map<AttendanceStatus, Integer> statusCounts) {
        this.name = name;
        this.penaltyCount = new PenaltyCount(statusCounts);
        this.penalty = penaltyCount.findAttendancePenalty();
    }
}
