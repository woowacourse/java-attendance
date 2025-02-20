package attendance.domain;

import java.util.List;
import java.util.Map;

public record AttendanceHistory(List<String> attendanceHistories, Map<AttendanceStatus, Integer> status) {
    public AbsenceStatusCount countAbsenceStatus() {
        int attendance = status().getOrDefault(AttendanceStatus.ATTENDANCE, 0);
        int absence = status().getOrDefault(AttendanceStatus.ABSENCE, 0);
        int late = status().getOrDefault(AttendanceStatus.LATE, 0);
        return new AbsenceStatusCount(absence, late, attendance);
    }
}
