package attendance.domain;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static attendance.domain.AttendanceStateType.EXPULSION;
import static attendance.domain.AttendanceStateType.LATE;
import static attendance.domain.AttendanceStateType.values;
import static attendance.domain.AttendanceWarningType.NONE;
import static attendance.domain.AttendanceWarningType.find;

public class AttendanceStatus implements Comparable<AttendanceStatus> {

    private final EnumMap<AttendanceStateType, Integer> status;
    private final AttendanceWarningType warningType;

    public AttendanceStatus(final List<Attendance> attendances) {
        status = calculateAttendanceStatus(attendances);
        warningType = calculateAttendanceRisk();
    }

    public static AttendanceStatus of(final List<Attendance> attendances) {
        return new AttendanceStatus(attendances);
    }

    public boolean isNotNoneState() {
        return warningType != NONE;
    }

    @Override
    public int compareTo(final AttendanceStatus other) {
        if (warningType == other.warningType) {
            int thisScore = calculateScore();
            int otherScore = other.calculateScore();

            return Integer.compare(thisScore, otherScore);
        }
        return this.warningType.compareTo(other.warningType);
    }

    public EnumMap<AttendanceStateType, Integer> getStatus() {
        return status;
    }

    public AttendanceWarningType getWarningType() {
        return warningType;
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

    private AttendanceWarningType calculateAttendanceRisk() {
        int expulsion = status.get(EXPULSION);
        int late = status.get(LATE);
        return find(expulsion, late);
    }

    private int calculateScore() {
        return status.get(EXPULSION) * 3 + status.get(LATE);
    }
}
