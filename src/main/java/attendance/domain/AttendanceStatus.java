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
        status = new EnumMap<>(AttendanceStateType.class);

        Arrays.stream(values())
                .forEach(stateType -> {
                    int stateCount = calculateStateCount(attendances, stateType);
                    status.put(stateType, stateCount);
                });

        int expulsion = status.get(EXPULSION);
        int late = status.get(LATE);

        warningType = find(expulsion, late);
    }

    public static AttendanceStatus of(final List<Attendance> attendances) {
        return new AttendanceStatus(attendances);
    }

    public boolean isNotNoneState() {
        return warningType != NONE;
    }

    public EnumMap<AttendanceStateType, Integer> getStatus() {
        return status;
    }

    public AttendanceWarningType getWarningType() {
        return warningType;
    }

    @Override
    public int compareTo(final AttendanceStatus other) {
        if (warningType == other.warningType) {
            if (this.calculateScore() == other.calculateScore()) {
                return 0;
            }
            return Integer.compare(other.calculateScore(), this.calculateScore());
        }
        return this.warningType.compareTo(other.warningType);
    }

    private int calculateScore() {
        return status.get(EXPULSION) * 3 + status.get(LATE);
    }

    private int calculateStateCount(final List<Attendance> attendances, final AttendanceStateType status) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.hasState(status))
                .count();
    }
}
