package domain;

import static domain.AttendanceStatus.ABSENCE;
import static domain.AttendanceStatus.LATENESS;

import java.util.Map;

public class StatisticsResult {

    private final Map<AttendanceStatus, Integer> result;
    private final Penalty penalty;

    public StatisticsResult(Map<AttendanceStatus, Integer> countResult) {
        this.result = countResult;
        this.penalty = Penalty.of(countResult.get(ABSENCE), countResult.get(LATENESS));
    }

    public Penalty getPenalty() {
        return penalty;
    }

    public int getCount(AttendanceStatus attendanceStatus) {
        return result.get(attendanceStatus);
    }

    public boolean hasPenalty() {
        return penalty != Penalty.NONE;
    }
}