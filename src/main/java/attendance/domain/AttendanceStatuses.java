package attendance.domain;

import java.util.Map;

public class AttendanceStatuses {

    private final AttendanceHistory attendanceHistory;
    private final Map<AttendanceStatus, Integer> status;

    public AttendanceStatuses(AttendanceHistory attendanceHistory, Map<AttendanceStatus, Integer> status) {
        this.attendanceHistory = attendanceHistory;
        this.status = status;
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
}
