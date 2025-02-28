package attendance.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceStatus {

    private final Map<AttendanceState, Integer> status;

    private AttendanceStatus(final Map<AttendanceState, Integer> status) {
        this.status = status;
    }

    public static AttendanceStatus fromAttendances(final List<Attendance> attendances) {
        return new AttendanceStatus(Arrays.stream(AttendanceState.values())
                .collect(Collectors.toMap(
                        state -> state,
                        state -> calculateState(attendances, state)
                )));
    }

    public int getStateCount(final AttendanceState state) {
        return status.get(state);
    }

    private static int calculateState(final List<Attendance> attendances, final AttendanceState state) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isSameState(state))
                .count();
    }
}
