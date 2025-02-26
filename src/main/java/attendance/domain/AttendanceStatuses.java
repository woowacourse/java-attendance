package attendance.domain;

import attendance.view.dto.AttendanceStatusCount;
import java.util.HashMap;
import java.util.Map;

public class AttendanceStatuses {

    private final Map<AttendanceStatus, Integer> status;
    private static final Integer STATUS_ADD_COUNT = 1;

    public AttendanceStatuses() {
        this.status = new HashMap<>();
    }

    public AttendanceDismissStatus calculateAttendanceDismiss() {
        return AttendanceDismissStatus.calculateAttendanceDismiss(absenceCount(), lateCount());
    }

    public AttendanceStatusCount attendanceStatusCount() {
        return new AttendanceStatusCount(absenceCount(), lateCount(), attendanceCount());
    }

    public void mergeStatus(AttendanceStatus attendanceStatus) {
        status.merge(attendanceStatus, STATUS_ADD_COUNT, Integer::sum);
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
}
