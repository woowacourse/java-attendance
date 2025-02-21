package dto;

import domain.AttendanceStatus;
import domain.AttendanceStatusStatistics;
import domain.Manage;

public record CrewAlmostExpelledResult(
        String nickname,
        AttendanceStatusStatistics attendanceStatusStatistics,
        Manage manage
) {
    
    public int calculateTotalAbsentCount() {
        return attendanceStatusStatistics.getStatusCounter().get(AttendanceStatus.LATE)
                + attendanceStatusStatistics.getStatusCounter().get(AttendanceStatus.ABSENT_LATE) * 3;
    }
}
