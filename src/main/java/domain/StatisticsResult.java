package domain;

import static domain.AttendanceStatus.ABSENCE;
import static domain.AttendanceStatus.ATTENDANCE;
import static domain.AttendanceStatus.LATENESS;

import java.util.HashMap;
import java.util.Map;

public class StatisticsResult {

    private final Map<AttendanceStatus, Integer> result;
    private final Penalty penalty;

    public StatisticsResult(int attendanceCount, int latenessCount, int absenceCount) {
        this.result = initializeResult(attendanceCount, latenessCount, absenceCount);
        this.penalty = Penalty.of(absenceCount, latenessCount);
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

    private Map<AttendanceStatus, Integer> initializeResult
        (int attendanceCount, int latenessCount, int absenceCount) {
        Map<AttendanceStatus, Integer> result = new HashMap<>();
        result.put(ATTENDANCE, attendanceCount);
        result.put(LATENESS, latenessCount);
        result.put(ABSENCE, absenceCount);

        return result;
    }
}
