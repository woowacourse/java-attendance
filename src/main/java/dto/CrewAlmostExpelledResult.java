package dto;

import domain.AttendanceStatus;
import domain.Manage;
import java.util.Map;

public record CrewAlmostExpelledResult(
        String nickname,
        Map<AttendanceStatus, Integer> attendanceStatusStatistics,
        Manage manage
) {

    public int calculateTotalAbsentCount() {
        return attendanceStatusStatistics.get(AttendanceStatus.LATE)
                + attendanceStatusStatistics.get(AttendanceStatus.ABSENT_LATE) * 3;
    }
}
