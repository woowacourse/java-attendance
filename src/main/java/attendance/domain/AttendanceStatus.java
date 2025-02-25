package attendance.domain;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static attendance.domain.AttendanceRiskType.NONE;
import static attendance.domain.AttendanceRiskType.find;
import static attendance.domain.AttendanceStateType.ABSENCE;
import static attendance.domain.AttendanceStateType.LATE;

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

            return Integer.compare(otherScore, thisScore);
        }
        return other.riskType.compareTo(this.riskType);
    }

    public EnumMap<AttendanceStateType, Integer> getStatus() {
        return status;
    }

    public AttendanceRiskType getRiskType() {
        return riskType;
    }

    private EnumMap<AttendanceStateType, Integer> calculateAttendanceStatus(final List<Attendance> attendances) {
        return Arrays.stream(AttendanceStateType.values())
                .collect(Collectors.toMap(
                        status -> status,
                        status -> calculateStateCount(attendances, status),
                        (s1, s2) -> s2,
                        () -> new EnumMap<>(AttendanceStateType.class)
                ));
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
