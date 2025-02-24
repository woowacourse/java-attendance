package attendance.domain;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static attendance.domain.AttendanceRiskType.NONE;
import static attendance.domain.AttendanceRiskType.find;
import static attendance.domain.AttendanceStateType.ABSENCE;
import static attendance.domain.AttendanceStateType.LATE;
import static attendance.domain.AttendanceStateType.values;

public class AttendanceStatus implements Comparable<AttendanceStatus> {

    private final EnumMap<AttendanceStateType, Integer> status;
    private final AttendanceRiskType riskType;

    public AttendanceStatus(final List<Attendance> attendances) {
        status = calculateAttendanceStatus(attendances);
        riskType = calculateAttendanceRisk();
    }

    public static AttendanceStatus of(final List<Attendance> attendances) {
        return new AttendanceStatus(attendances);
    }

    public boolean isNotNoneState() {
        return riskType != NONE;
    }

    @Override
    public int compareTo(final AttendanceStatus other) {
        if (riskType == other.riskType) {
            int thisScore = calculateScore();
            int otherScore = other.calculateScore();

            return Integer.compare(thisScore, otherScore);
        }
        return this.riskType.compareTo(other.riskType);
    }

    public EnumMap<AttendanceStateType, Integer> getStatus() {
        return status;
    }

    public AttendanceRiskType getRiskType() {
        return riskType;
    }

    private EnumMap<AttendanceStateType, Integer> calculateAttendanceStatus(final List<Attendance> attendances) {
        EnumMap<AttendanceStateType, Integer> status = new EnumMap<>(AttendanceStateType.class);
        Arrays.stream(values())
                .forEach(stateType -> {
                    int stateCount = calculateStateCount(attendances, stateType);
                    status.put(stateType, stateCount);
                });
        return status;
    }

    private int calculateStateCount(final List<Attendance> attendances, final AttendanceStateType status) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.hasState(status))
                .count();
    }

    private AttendanceRiskType calculateAttendanceRisk() {
        int expulsion = status.get(ABSENCE);
        int late = status.get(LATE);
        return find(expulsion, late);
    }

    private int calculateScore() {
        return status.get(ABSENCE) * 3 + status.get(LATE);
    }
}
