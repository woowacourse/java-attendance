package attendance.domain;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static attendance.domain.AttendanceRisk.NONE;
import static attendance.domain.AttendanceRisk.find;
import static attendance.domain.AttendanceState.ABSENCE;
import static attendance.domain.AttendanceState.LATE;

public class AttendanceStatus implements Comparable<AttendanceStatus> {

    private final EnumMap<AttendanceState, Integer> status;
    private final AttendanceRisk risk;

    public AttendanceStatus(final List<Attendance> attendances) {
        status = calculateAttendanceStatus(attendances);
        risk = calculateAttendanceRisk();
    }

    public static AttendanceStatus of(final List<Attendance> attendances) {
        return new AttendanceStatus(attendances);
    }

    public boolean isNotNoneState() {
        return risk != NONE;
    }

    @Override
    public int compareTo(final AttendanceStatus other) {
        if (risk == other.risk) {
            int thisScore = calculateScore();
            int otherScore = other.calculateScore();

            return Integer.compare(otherScore, thisScore);
        }
        return other.risk.compareTo(this.risk);
    }

    public EnumMap<AttendanceState, Integer> getStatus() {
        return status;
    }

    public AttendanceRisk getRisk() {
        return risk;
    }

    private EnumMap<AttendanceState, Integer> calculateAttendanceStatus(final List<Attendance> attendances) {
        return Arrays.stream(AttendanceState.values())
                .collect(Collectors.toMap(
                        status -> status,
                        status -> calculateStateCount(attendances, status),
                        (s1, s2) -> s2,
                        () -> new EnumMap<>(AttendanceState.class)
                ));
    }

    private int calculateStateCount(final List<Attendance> attendances, final AttendanceState status) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.hasState(status))
                .count();
    }

    private AttendanceRisk calculateAttendanceRisk() {
        int expulsion = status.get(ABSENCE);
        int late = status.get(LATE);
        return find(expulsion, late);
    }

    private int calculateScore() {
        return status.get(ABSENCE) * 3 + status.get(LATE);
    }
}
