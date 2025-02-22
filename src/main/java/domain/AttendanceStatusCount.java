package domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceStatusCount {
    public static final int LATE_TO_ABSENT_THRESHOLD = 3;
    private final Map<AttendanceStatus, Integer> statuses = initStatuses();

    public void updateStatus(Attendance attendance) {
        AttendanceStatus status = attendance.calculateAttendanceStatus();
        statuses.put(status, statuses.get(status) + 1);
    }

    private Map<AttendanceStatus, Integer> initStatuses() {
        Map<AttendanceStatus, Integer> statuses = new HashMap<>();
        for (AttendanceStatus status : AttendanceStatus.values()) {
            statuses.put(status, 0);
        }
        return statuses;
    }

    public void deleteStatus(Attendance attendance) {
        AttendanceStatus status = attendance.calculateAttendanceStatus();
        statuses.put(status, statuses.get(status) - 1);
    }

    public AttendanceAlertLevel calculateAttendanceAlertLevel() {
        int absentTotal = statuses.get(AttendanceStatus.ABSENT) + (statuses.get(AttendanceStatus.LATE)
                / LATE_TO_ABSENT_THRESHOLD);

        return AttendanceAlertLevel.calculateAttendanceAlertLevel(absentTotal);
    }

    public Map<AttendanceStatus, Integer> getStatuses() {
        return statuses;
    }
}
