package attendance.domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceStatuses {

    private final Map<AttendanceStatus, Integer> status;
    private static final Integer STATUS_ADD_COUNT = 1;

    public AttendanceStatuses(Map<AttendanceStatus, Integer> status) {
        this.status = status;
    }

    public AttendanceStatuses() {
        this.status = new HashMap<>();
    }

    public int attendanceCount() {
        return status.getOrDefault(AttendanceStatus.ATTENDANCE, 0);
    }

    public int lateCount() {
        return status.getOrDefault(AttendanceStatus.LATE, 0);
    }

    public int absenceCount() {
        return status.getOrDefault(AttendanceStatus.ABSENCE, 0);
    }

    public AttendanceDismissStatus calculateAttendanceDismiss() {
        return AttendanceDismissStatus.calculateAttendanceDismiss(absenceCount(), lateCount());
    }

    public void mergeStatus(AttendanceStatus attendanceStatus) {
        status.merge(attendanceStatus, STATUS_ADD_COUNT, Integer::sum);
    }
}
