package attendance.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum AttendanceState {

    ATTENDANCE(0), TARDINESS(5), ABSENCE(30);

    private static final List<AttendanceState> SORTED_ATTENDANCE_STATE = Arrays.stream(AttendanceState.values())
            .sorted(Comparator.comparingInt(AttendanceState::getThreshold).reversed())
            .toList();

    private final int threshold;

    AttendanceState(final int threshold) {
        this.threshold = threshold;
    }

    public static AttendanceState from(final long diff) {
        return SORTED_ATTENDANCE_STATE.stream()
                .filter(attendanceState -> attendanceState.threshold < diff)
                .findAny()
                .orElse(ATTENDANCE);
    }

    public int getThreshold() {
        return threshold;
    }
}
