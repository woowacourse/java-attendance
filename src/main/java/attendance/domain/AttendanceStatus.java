package attendance.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceStatus implements Comparable<AttendanceStatus> {

    private final Map<AttendanceState, Integer> status;
    private final AttendanceRisk risk;

    private AttendanceStatus(final Map<AttendanceState, Integer> status, final AttendanceRisk risk) {
        this.status = status;
        this.risk = risk;
    }

    public static AttendanceStatus fromAttendances(final List<Attendance> attendances) {
        Map<AttendanceState, Integer> status = Arrays.stream(AttendanceState.values())
                .collect(Collectors.toMap(
                        state -> state,
                        state -> calculateState(attendances, state)
                ));

        AttendanceRisk risk = AttendanceRisk.evaluate(
                status.get(AttendanceState.ABSENCE),
                status.get(AttendanceState.TARDY)
        );
        return new AttendanceStatus(status, risk);
    }

    public int getAbsenceStateCount() {
        return status.get(AttendanceState.ABSENCE);
    }

    public int getTardyStateCount() {
        return status.get(AttendanceState.TARDY);
    }

    public int getAttendanceStateCount() {
        return status.get(AttendanceState.ATTENDANCE);
    }

    public AttendanceRisk getRisk() {
        return risk;
    }

    private static int calculateState(final List<Attendance> attendances, final AttendanceState state) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isSameState(state))
                .count();
    }

    @Override
    public int compareTo(final AttendanceStatus o) {
        if (risk.equals(o.risk)) {

            int o1Score = status.get(AttendanceState.ABSENCE) + status.get(AttendanceState.TARDY) * 3;
            int o2Score = o.status.get(AttendanceState.ABSENCE) + o.status.get(AttendanceState.TARDY) * 3;

            return Integer.compare(o2Score, o1Score);
        }
        return risk.compareTo(o.risk);
    }
}
